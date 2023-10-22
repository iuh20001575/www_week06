package vn.edu.iuh.fit;

import com.thedeanda.lorem.LoremIpsum;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.backend.models.Post;
import vn.edu.iuh.fit.backend.models.PostComment;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.PostRepository;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class PostCommentTests {

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    void insert() {
        Random random = new Random();
        for (int i = 1; i<= 200; i++) {
            PostComment postComment = new PostComment(
                     new Post(random.nextLong(11),
                    new User(random.nextLong(11),
                    null,
                    LoremIpsum.getInstance().getTitle(50),
                    true,
                    Instant.now(),
                    Instant.now(),
                    LoremIpsum.getInstance().getParagraphs(100, 500)
            );
        }
    }

    @Test
    void contextLoads() {
        List<Post> posts = postRepository.findAll();

        Assertions.assertFalse(posts.isEmpty());
    }
}
