package vn.edu.iuh.fit;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.PostComment;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostCommentRepository;
import vn.edu.iuh.fit.backend.repositories.PostRepository;
import vn.edu.iuh.fit.backend.repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class WwwWeek06Application {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public WwwWeek06Application(UserRepository userRepository, PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WwwWeek06Application.class, args);
    }

    //        @Bean
    public CommandLineRunner insert() {
        return args -> {
//                Add users
            User user;
            for (int i = 0; i < 100; i++) {
                user = new User(
                        LoremIpsum.getInstance().getFirstName(),
                        LoremIpsum.getInstance().getLastName(),
                        LoremIpsum.getInstance().getLastName(),
                        LoremIpsum.getInstance().getPhone(),
                        LoremIpsum.getInstance().getEmail(),
                        BCrypt.withDefaults().hashToString(5, LoremIpsum.getInstance().getPhone().toCharArray()),
                        Instant.now(), null, LoremIpsum.getInstance().getTitle(100),
                        LoremIpsum.getInstance().getWords(1000)
                );

                userRepository.save(user);
            }
            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);

//                Add posts
            Random random = new Random();

            for (int i = 0; i < 1000; i++) {
                Post post = new Post(
                        users.get(random.nextInt(50) + 1),
                        null,
                        LoremIpsum.getInstance().getTitle(3),
                        LoremIpsum.getInstance().getTitle(8),
                        LoremIpsum.getInstance().getParagraphs(50, 100),
                        true,
                        Instant.now(),
                        Instant.now(),
                        Instant.now(),
                        LoremIpsum.getInstance().getParagraphs(50, 100)
                );

                postRepository.save(post);
            }

            postRepository.findAll().forEach(System.out::println);

//                Add comments
            for (int i = 1; i <= 1000; i++) {
                PostComment postComment = new PostComment(
                        new Post(random.nextLong(1000) + 1),
                        new User(random.nextLong(100) + 1),
                        null,
                        LoremIpsum.getInstance().getTitle(3),
                        true,
                        Instant.now(),
                        Instant.now(),
                        LoremIpsum.getInstance().getParagraphs(50, 100)
                );

                postCommentRepository.save(postComment);
            }

            postCommentRepository.findAll().forEach(System.out::println);
        };
    }
}
