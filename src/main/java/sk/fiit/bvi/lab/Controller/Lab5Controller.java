package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Dto.Lab5EditProfileDto;
import sk.fiit.bvi.lab.Entity.UserLab5;
import sk.fiit.bvi.lab.Service.Lab5Service;
import sk.fiit.bvi.lab.Utils.AlertUtils;
import sk.fiit.bvi.lab.Utils.Constants;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab5Controller {
    public static final Logger LOGGER = Logger.getLogger("Lab1Controller");
    private final Lab5Service service;

    @Autowired
    public Lab5Controller(Lab5Service service) {
        this.service = service;
    }

    @GetMapping(path = {"/lab5/", "/lab5"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return "lab5/intro";
    }

    @GetMapping("/lab5/login")
    public String loginGet(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(null, null, session, model);
    }

    @PostMapping("/lab5/verify-ctf")
    public String verifyCtf(@RequestParam(required = false) String toVerify, HttpSession session, Model model) {
        if(service.getCTF().equals(toVerify)) {
            session.setAttribute("lab5Completed", true);
        }
        return "lab5/intro";
    }

    @PostMapping("/lab5/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute(Constants.PROFILE_ID_LAB_5);
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            LoginWrapper loginWrapper = service.getUsers(profileId, password);
            model.addAttribute("executedQuery", loginWrapper.getQuery());

            if(!loginWrapper.getUsers().isEmpty()) {
                session.setAttribute(Constants.PROFILE_ID_LAB_5, profileId);
                model.addAttribute("users", loginWrapper.getUsers());
                if(loginWrapper.getUsers().size() > 1) {
                }
                return home(session, model);
            } else {
                AlertUtils.addAlertToModel(model, "Account does not exists", AlertUtils.AlertType.DANGER);
            }
        }
        return "lab5/login";
    }

    @GetMapping("lab5/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_5);
        if(null != profileId) {
            List<UserLab5> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab5/home";
        }
        return "lab5/login";
    }

    @GetMapping("lab5/profile/edit")
    public String profileEdit(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_5);
        if(null != profileId) {
            List<UserLab5> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab5/profile-edit";
        }
        return "lab5/home";
    }

    @PostMapping("lab5/profile/edit")
    public String performProfileEdit(Lab5EditProfileDto newProfileData, HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_5);
        if(null != profileId) {
            service.editUser(profileId, newProfileData);
            List<UserLab5> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
                model.addAttribute("lab5CTF", service.getCTF());
            }
        }
        return "lab5/home";
    }

    @GetMapping("lab5/logout")
    public String logout(HttpSession session) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_5);
        if(null != profileId) {
            session.removeAttribute(Constants.PROFILE_ID_LAB_5);
            return "lab5/login";
        }
        return "lab5/login";
    }
}
