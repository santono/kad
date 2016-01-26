package kad.reports.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.net.URLEncoder;

@Controller
//@RequestMapping

public class AccessController {

    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(required = false) String message) {
        logger.debug("Received request to login " + message);

        model.addAttribute("message", message);
        //   return "access/login";
        return "access/signin";
    }

    @RequestMapping("/signin")
    public String signin(Model model, @RequestParam(required = false) String message) {
        logger.debug("Received request to signin   1 " + message);
        String viewName;
//        if (message==null) {
//            message="";
//            viewName="logout";
//        } else {
//            viewName="access/signin";
//        }
        viewName = "access/signin";

        model.addAttribute("message", message);
        //   return "access/login";
        return viewName;
    }

    @RequestMapping(value = "/denied")
    public String denied() {
        logger.debug("Received request to denied ");
        return "access/denied";
    }


    //  @RequestMapping(value = "/login/failure")
    @RequestMapping(value = "/signin/failure")
    public String loginFailure() {
        logger.debug("Received request to loginFailure ");
        String message = "Ошибка входа!";
        //    return "redirect:/login?message="+message;
        //  return "redirect:/signin?message="+message;
        try {
            return "redirect:/signin?message=" + URLEncoder.encode(message, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return "redirect:/signin?message=login failure!";
        }

    }


    @RequestMapping(value = "/logout")
    public String logout() {
        //    String message = "Выход успешный!";
        String message = "";
        //     return "redirect:/login?message="+message;
        //    return "redirect:/signin?message="+message;
        return "redirect:/signin";
    }

    @RequestMapping(value = "/logout/success")
    public String logoutSuccess() {
        //    String message = "Выход успешный!";
        String message = "";
        //     return "redirect:/login?message="+message;
        //    return "redirect:/signin?message="+message;
        try {
            return "redirect:/signin?message=" + URLEncoder.encode(message, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return "redirect:/signin?message=Successful Exit!";
        }
    }
}
