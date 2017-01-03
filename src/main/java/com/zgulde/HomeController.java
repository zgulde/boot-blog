package com.zgulde;

import com.zgulde.posts.PostRepository;
import com.zgulde.users.User;
import com.zgulde.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController extends BaseController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile(Model m) {
        User user = loggedInUser();
        m.addAttribute("user", user);
        // is there a better way to do this?
        m.addAttribute("posts", postRepository.findByUserId(user.getId()));
        return "profile";
    }

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception e) {
        e.printStackTrace();
        return "500";
    }
}
