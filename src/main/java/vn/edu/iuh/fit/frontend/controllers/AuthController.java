package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.repositories.PostRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(name = "/")
public class AuthController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int pageNum = page.orElse(1);
        int sizeNum = size.orElse(10);

        PageRequest pageable = PageRequest.of(pageNum-1, sizeNum, Sort.by("publishedAt"));

        Page<Post> posts = postRepository.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("posts", posts);
        modelAndView.addObject("pages", IntStream.rangeClosed(1, posts.getTotalPages()).boxed().collect(Collectors.toList()));

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value = {"/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("login");

        return modelAndView;
    }
}
