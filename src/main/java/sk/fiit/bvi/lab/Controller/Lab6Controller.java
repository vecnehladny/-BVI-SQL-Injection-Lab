package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Entity.UserLab6;
import sk.fiit.bvi.lab.Service.Lab6Service;
import sk.fiit.bvi.lab.Utils.AlertUtils;
import sk.fiit.bvi.lab.Utils.Constants;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab6Controller {
    public static final Logger LOGGER = Logger.getLogger("Lab1Controller");
    private final Lab6Service service;

    @Autowired
    public Lab6Controller(Lab6Service service) {
        this.service = service;
    }

    @GetMapping(path = {"/lab6/", "/lab6"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return "lab6/intro";
    }

    @GetMapping("/lab6/login")
    public String loginGet(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(null, null, session, model);
    }

    @PostMapping("/lab6/verify-ctf")
    public String verifyCtf(@RequestParam(required = false) String toVerify, HttpSession session, Model model) {
        if(service.getCTF().equals(toVerify)) {
            session.setAttribute("lab6Completed", true);
        }
        return "lab6/intro";
    }

    @PostMapping("/lab6/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute(Constants.PROFILE_ID_LAB_6);
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            LoginWrapper loginWrapper = service.getUsers(profileId, password);
            model.addAttribute("executedQuery", loginWrapper.getQuery());

            if(!loginWrapper.getUsers().isEmpty()) {
                session.setAttribute(Constants.PROFILE_ID_LAB_6, profileId);
                model.addAttribute("users", loginWrapper.getUsers());
                if(loginWrapper.getUsers().size() > 1) {
                }
                return home(session, model);
            } else {
                AlertUtils.addAlertToModel(model, "Account does not exists", AlertUtils.AlertType.DANGER);
            }
        }
        return "lab6/login";
    }

    @GetMapping("lab6/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_6);
        if(null != profileId) {
            List<UserLab6> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                UserLab6 currUser = users.get(0);
                if("pIRIo5SdozzTCElqyAa5G5qnOddrhX9Bio9l58eHZGA=".equals(currUser.getName())) {
                    model.addAttribute("lab6CTF", service.getCTF());
                }
                model.addAttribute("currUser", currUser);
            }
            return "lab6/home";
        }
        return "lab6/login";
    }

    @GetMapping("lab6/logout")
    public String logout(HttpSession session) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID_LAB_6);
        if(null != profileId) {
            session.removeAttribute(Constants.PROFILE_ID_LAB_6);
            return "lab6/login";
        }
        return "lab6/login";
    }
}
