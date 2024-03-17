package note.javadeveloper.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(ModelAndView modelAndView) {
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
