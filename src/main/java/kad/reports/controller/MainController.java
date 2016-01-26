package kad.reports.controller;

import javax.servlet.http.HttpSession;

import kad.reports.service.*;
import kad.reports.util.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {
    @Autowired
    private UsersRolesService urService;
    @Autowired
    private YaService yaService;

	protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(value="/mainpage",method = RequestMethod.GET)
    public String mainUserPage(Model model,HttpSession session) {
        UserInfo userInfo=new UserInfo();
        String viewName;
        int shifrPodr;
        shifrPodr=(int)userInfo.getUserDTO().getShifrPodr();
        model.addAttribute("shifrPodr",shifrPodr);
        viewName="t1";

        return viewName;
    }




    // Total control - setup a model and return the view name yourself. Or consider
    // subclassing ExceptionHandlerExceptionResolver (see below).
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", exception);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("errors/customerror");
//        return mav;
//    }
    @RequestMapping(value="/ya",method = RequestMethod.GET)
    public String makeYaGeo(Model model,HttpSession session) {
        String viewName;
        logger.debug("before ya service");
        yaService.performCodingForAllCityes();
        logger.debug("after ya service");
        viewName="t1";
        return viewName;
    }
    @RequestMapping(value="/yaxx",method = RequestMethod.GET)
    public String makeExtractionGeo(Model model,HttpSession session) {
        String viewName;
        logger.debug("before ya service");
        yaService.extractCodingForAllCityes();
        logger.debug("after ya service");
        viewName="t1";
        return viewName;
    }

}

    

