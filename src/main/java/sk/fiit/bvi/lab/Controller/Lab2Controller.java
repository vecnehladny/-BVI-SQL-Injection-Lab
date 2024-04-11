package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Entity.UserLab2;
import sk.fiit.bvi.lab.Service.Lab2Service;
import sk.fiit.bvi.lab.Utils.AlertUtils;
import sk.fiit.bvi.lab.Utils.Constants;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab2Controller {

    public static final Logger LOGGER = Logger.getLogger("Lab2Controller");

    private final Lab2Service service;

    @Autowired
    public Lab2Controller(Lab2Service service) {
        this.service = service;
    }

    @GetMapping(path = {"/lab2/", "/lab2"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return "lab2/intro";
    }

    @GetMapping("/lab2/login")
    public String loginGet(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(null, null, session, model);
    }

    @PostMapping("/lab2/verify-ctf")
    public String verifyCtf(@RequestParam(required = false) String toVerify, HttpSession session, Model model) {
        if(service.getCTF().equals(toVerify)) {
            session.setAttribute("lab2Completed", true);
        }
        return "lab2/intro";
    }

    @PostMapping("/lab2/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute(Constants.PROFILE_ID_LAB_2);
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            LoginWrapper loginWrapper = service.getUsers(profileId, password);
            model.addAttribute("executedQuery", loginWrapper.getQuery());
            if(!loginWrapper.getUsers().isEmpty()) {
                session.setAttribute(Constants.PROFILE_ID_LAB_2, profileId);
                model.addAttribute("users", loginWrapper.getUsers());
                if(loginWrapper.getUsers().size() > 1) {
                    model.addAttribute("lab2CTF", service.getCTF());
                }
                return home(session, model);
            } else {
                AlertUtils.addAlertToModel(model, "Account does not exists", AlertUtils.AlertType.DANGER);
            }
        }
        return Constants.LAB_2_LOGIN_VIEW;
    }

    @GetMapping("/lab2/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_2);
        if(null != profileId) {
            List<UserLab2> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab2/home";
        }
        return Constants.LAB_2_LOGIN_VIEW;
    }

    @GetMapping("/lab2/logout")
    public String logout(HttpSession session) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_2);
        if(null != profileId) {
            session.removeAttribute(Constants.PROFILE_ID_LAB_2);
            return Constants.LAB_2_LOGIN_VIEW;
        }
        return Constants.LAB_2_LOGIN_VIEW;
    }
}
