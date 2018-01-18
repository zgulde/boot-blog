package com.zgulde.posts;

import com.zgulde.BaseController;
import com.zgulde.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostsController extends BaseController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;

    @GetMapping
    public String index(Model m) {
        List<Post> posts = postRepository.findAll();
        m.addAttribute("posts", posts);
        return "posts/index";
    }

    @PostMapping("/create")
    public String save(@Valid Post post, Errors errors, Model view, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            view.addAttribute("errors", errors);
            view.addAttribute("post", post);
            view.addAttribute("tags", tagRepository.findAll());
            return "posts/create";
        }
        post.setUser(loggedInUser());
        Long id = postRepository.save(post).getId();
        ra.addFlashAttribute("successMessage", "Post Successfully Created!");
        return "redirect:/posts/" + id;
    }

    @GetMapping("/create")
    public String create(Model m) {
        m.addAttribute("post", new Post());
        m.addAttribute("tags", tagRepository.findAll());
        return "posts/create";
    }

    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute Post p, @PathVariable long id) {
        Post post = postRepository.findOne(id);
        // access control
        if (post.getUser().getId() != loggedInUser().getId()) {
            return "redirect:/posts";
        }
        // update with new values
        post.setTitle(p.getTitle());
        post.setBody(p.getBody());
        postRepository.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model m, @PathVariable long id) {
        Post post = postRepository.findOne(id);
        // access control
        if (post.getUser().getId() != loggedInUser().getId()) {
            return "redirect:/posts";
        }
        m.addAttribute("post", post);
        return "posts/edit";
    }

    @GetMapping("/{id}")
    public String show(Model m, @PathVariable long id) {
        m.addAttribute(postRepository.findOne(id));
        return "posts/show";
    }
}
