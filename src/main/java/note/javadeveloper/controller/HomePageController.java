package note.javadeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @GetMapping("/")
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        modelAndView.setViewName("home/home");
        return modelAndView;
    }
}
