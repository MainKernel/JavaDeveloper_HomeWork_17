package note.javadeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import note.javadeveloper.dto.LoginFormDto;
import note.javadeveloper.dto.UserDto;
import note.javadeveloper.entity.UserEntity;
import note.javadeveloper.repository.UserRepository;
import note.javadeveloper.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginRegisterController {
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    private PasswordEncoder passwordEncoder;
    private RegistrationService registrationService;
    private UserRepository userRepository;

    @Autowired
    private void set(RegistrationService registrationService,
                     UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }


    @GetMapping("/register")
    public ModelAndView getRegisterPage(ModelAndView modelAndView) {
        modelAndView.addObject("user", new UserDto());
        modelAndView.setViewName("register/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView postRegistrationPage(ModelAndView modelAndView, @ModelAttribute UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        registrationService.registerUser(userDto);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(ModelAndView modelAndView, @ModelAttribute("user") LoginFormDto loginFormDto) {
        modelAndView.addObject("user", new LoginFormDto());
        modelAndView.setViewName("login/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public void postLoginPage(@ModelAttribute("name") LoginFormDto loginFormDto,
                              HttpSession session) {

        String encode = passwordEncoder.encode(loginFormDto.getPassword());
        loginFormDto.setPassword(encode);
        Long userId = userRepository
                .findByUsername(loginFormDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username doesn't exits")).getId();
        session.setAttribute("userId", userId);
    }

    @GetMapping("/logout")
    public ModelAndView modelAndView(ModelAndView modelAndView, Authentication authentication,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        logoutHandler.logout(request, response, authentication);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error/error";
    }
}
