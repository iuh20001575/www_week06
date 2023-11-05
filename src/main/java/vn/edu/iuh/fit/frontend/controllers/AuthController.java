package vn.edu.iuh.fit.frontend.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.PostComment;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostCommentRepository;
import vn.edu.iuh.fit.backend.repositories.PostRepository;
import vn.edu.iuh.fit.backend.repositories.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(name = "/")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/", "/index", "/posts"})
    public ModelAndView index(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int pageNum = page.orElse(1);
        int sizeNum = size.orElse(10);

        PageRequest pageable = PageRequest.of(pageNum - 1, sizeNum, Sort.by("publishedAt"));

        Page<Post> posts = postRepository.findAllByPublished(true, pageable);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("posts", posts);
        modelAndView.addObject("pages", IntStream.rangeClosed(1, posts.getTotalPages()).boxed().collect(Collectors.toList()));

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "login";
    }

    @PostMapping(value = {"/login"})
    public String handleLogin(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isEmpty() || !BCrypt.verifyer().verify(user.getPasswordHash().getBytes(), userOptional.get().getPasswordHash().getBytes()).verified) {
            model.addAttribute("user", user);
            model.addAttribute("error", true);

            return "login";
        }

        return "redirect:/index";
    }

    @GetMapping(value = {"/posts/{id}"})
    public ModelAndView postDetail(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Long idLong = Long.parseLong(id);
            Optional<Post> post = postRepository.findById(idLong);


            if (post.isPresent()) {
                PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("createdAt").descending());

                Page<PostComment> comments = postCommentRepository.findAllByPostId(idLong, pageRequest);
                System.out.println(comments);

                modelAndView.addObject("post", post.get());
                modelAndView.addObject("comments", comments);

                modelAndView.setViewName("posts/detail");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            modelAndView.setViewName("notFound");
        }

        return modelAndView;
    }
}
