package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Entity.UserLab3;
import sk.fiit.bvi.lab.Service.Lab3Service;
import sk.fiit.bvi.lab.Utils.AlertUtils;
import sk.fiit.bvi.lab.Utils.Constants;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab3Controller {

    public static final Logger LOGGER = Logger.getLogger("Lab3Controller");

    private final Lab3Service service;

    @Autowired
    public Lab3Controller(Lab3Service service) {
        this.service = service;
    }

    @GetMapping(path = {"/lab3/", "/lab3"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return "lab3/intro";
    }

    @GetMapping("/lab3/login")
    public String loginGet(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(profileId, password, session, model);
    }

    @PostMapping("/lab3/verify-ctf")
    public String verifyCtf(@RequestParam(required = false) String toVerify, HttpSession session, Model model) {
        if(service.getCTF().equals(toVerify)) {
            session.setAttribute("lab3Completed", true);
        }
        return "lab3/intro";
    }

    @PostMapping("/lab3/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute(Constants.PROFILE_ID_LAB_3);
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            LoginWrapper loginWrapper = service.getUsers(profileId, password);
            model.addAttribute("executedQuery", loginWrapper.getQuery());
            if(!loginWrapper.getUsers().isEmpty()) {
                session.setAttribute(Constants.PROFILE_ID_LAB_3, profileId);
                model.addAttribute("users", loginWrapper.getUsers());
                if(loginWrapper.getUsers().size() > 1) {
                    model.addAttribute("lab3CTF", service.getCTF());
                }
                return home(session, model);
            } else {
                AlertUtils.addAlertToModel(model, "Account does not exists", AlertUtils.AlertType.DANGER);
            }
        }
        return Constants.LAB_3_LOGIN_VIEW;
    }

    @GetMapping("/lab3/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_3);
        if(null != profileId) {
            List<UserLab3> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab3/home";
        }
        return Constants.LAB_3_LOGIN_VIEW;
    }

    @GetMapping("/lab3/logout")
    public String logout(HttpSession session) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_3);
        if(null != profileId) {
            session.removeAttribute(Constants.PROFILE_ID_LAB_3);
            return Constants.LAB_3_LOGIN_VIEW;
        }
        return Constants.LAB_3_LOGIN_VIEW;
    }
}
