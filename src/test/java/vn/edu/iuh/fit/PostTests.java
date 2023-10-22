package vn.edu.iuh.fit;

import com.thedeanda.lorem.LoremIpsum;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostRepository;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class PostTests {

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    void insert() {
        User user1 = new User(1L);
        User user2 = new User(2L);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Post post = new Post(
                    random.nextInt(2) == 0 ? user1 : user2,
                    null,
                    LoremIpsum.getInstance().getTitle(100),
                    LoremIpsum.getInstance().getTitle(1000),
                    LoremIpsum.getInstance().getParagraphs(50, 1000),
                    true,
                    Instant.now(),
                    Instant.now(),
                    Instant.now(),
                    LoremIpsum.getInstance().getParagraphs(300, 1000)
            );

            postRepository.save(post);
        }
    }

    @Test
    void contextLoads() {
        List<Post> posts = postRepository.findAll();

        Assertions.assertFalse(posts.isEmpty());
    }
}
