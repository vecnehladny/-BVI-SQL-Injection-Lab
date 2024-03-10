package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Entity.UserLab2;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Entity.mapper.UserLab2Mapper;
import sk.fiit.bvi.lab.Wrapper.Alert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab3Controller {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final Logger LOGGER = Logger.getLogger("HomeController");

    @GetMapping(path = {"/lab3/", "/lab3"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(null, null, session, model);
    }

    @GetMapping("/lab3/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute("profileId");
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            String query = String.format("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab3 u WHERE u.profile_id='%s' AND u.password='%s'", profileId, encoded);
            model.addAttribute("executedQuery", query);
            List<String> users = jdbcTemplate.query(query, new StringMapper());

            if(!users.isEmpty()) {
                session.setAttribute("profileId", profileId);
                model.addAttribute("users", users);
                return home(session, model);
            } else {
                model.addAttribute("alerts",
                                   Collections.singletonList(Alert.builder()
                                                                  .message("Account does not exists")
                                                                  .type("danger")
                                                                  .build()));
            }
        }
        return "lab3/login";
    }

    @GetMapping("lab3/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute("profileId");
        if(null != profileId) {
            String query = String.format("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab3 u WHERE u.profile_id='%s'", profileId);
            List<UserLab2> users = jdbcTemplate.query(query, new UserLab2Mapper());
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab3/home";
        }
        return "lab3/login";
    }

    @GetMapping("lab3/logout")
    public String logout(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute("profileId");
        if(null != profileId) {
            session.removeAttribute("profileId");
            return "lab3/login";
        }
        return "lab3/login";
    }
}
