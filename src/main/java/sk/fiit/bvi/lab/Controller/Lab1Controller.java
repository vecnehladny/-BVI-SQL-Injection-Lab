package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.bvi.lab.Entity.UserLab1;
import sk.fiit.bvi.lab.Service.Lab1Service;
import sk.fiit.bvi.lab.Utils.AlertUtils;
import sk.fiit.bvi.lab.Utils.Constants;
import sk.fiit.bvi.lab.Wrapper.Lab1LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class Lab1Controller {
    public static final Logger LOGGER = Logger.getLogger("Lab1Controller");
    private final Lab1Service service;

    @Autowired
    public Lab1Controller(Lab1Service service) {
        this.service = service;
    }

    @GetMapping(path = {"/lab1/", "/lab1"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return login(null, null, session, model);
    }

    @GetMapping("/lab1/login")
    public String login(@RequestParam(required = false) String profileId, @RequestParam(required = false) String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        String profileIdCurr = (String) session.getAttribute(Constants.PROFILE_ID);
        if(null != profileIdCurr) { return home(session, model); }
        if(null != password) {
            Lab1LoginWrapper loginWrapper = service.getUsers(profileId, password);
            model.addAttribute("executedQuery", loginWrapper.getQuery());

            if(!loginWrapper.getUsers().isEmpty()) {
                session.setAttribute(Constants.PROFILE_ID, profileId);
                model.addAttribute("users", loginWrapper.getUsers());
                return home(session, model);
            } else {
                AlertUtils.addAlertToModel(model, "Account does not exists", AlertUtils.AlertType.DANGER);
            }
        }
        return "lab1/login";
    }

    @GetMapping("lab1/home")
    public String home(HttpSession session, Model model) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID);
        if(null != profileId) {
            List<UserLab1> users = service.getUser(profileId);
            if(!users.isEmpty()) {
                model.addAttribute("currUser", users.get(0));
            }
            return "lab1/home";
        }
        return "lab1/login";
    }

    @GetMapping("lab1/logout")
    public String logout(HttpSession session) {
        String profileId = (String) session.getAttribute(Constants.PROFILE_ID);
        if(null != profileId) {
            session.removeAttribute(Constants.PROFILE_ID);
            return "lab1/login";
        }
        return "lab1/login";
    }
}
