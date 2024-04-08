package sk.fiit.bvi.lab.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Controller
public class HomeController {
    public static final Logger LOGGER = Logger.getLogger("HomeController");

    @GetMapping(path = {"/"})
    public String root(HttpSession session, Model model) throws NoSuchAlgorithmException {
        return "intro";
    }
}
