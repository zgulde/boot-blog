package com.zgulde.posts;

import com.zgulde.BaseController;
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

    private PostRepository postRepository;
    private TagRepository tagRepository;

    public PostsController(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public String index(Model m) {
        List<Post> posts = postRepository.findAll();
        m.addAttribute("posts", posts);
        return "posts/index";
    }

    @PostMapping("/create")
    public String save(@Valid Post post, Errors errors, Model view, RedirectAttributes ra) {
        if (errors.hasErrors()) {
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
    public String update(@Valid Post p, Errors errors, @PathVariable long id) {
        Post post = postRepository.findOne(id);
        // access control
        if (post.getUser().getId() != loggedInUser().getId()) {
            return "redirect:/posts";
        }
        if (errors.hasErrors()) {
            return "posts/" + id + "/edit";
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
