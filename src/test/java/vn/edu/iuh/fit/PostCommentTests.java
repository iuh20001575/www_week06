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
import vn.edu.iuh.fit.backend.repositories.PostCommentRepository;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class PostCommentTests {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @PostConstruct
    void insert() {
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            PostComment postComment = new PostComment(
                    new Post(4L),
                    new User(random.nextLong(11)+1),
                    new PostComment(1012L),
                    LoremIpsum.getInstance().getTitle(10),
                    true,
                    Instant.now(),
                    Instant.now(),
                    LoremIpsum.getInstance().getParagraphs(2, 4)
            );

            postCommentRepository.save(postComment);
        }
    }

    @Test
    void contextLoads() {
        List<PostComment> posts = postCommentRepository.findAll();

        Assertions.assertFalse(posts.isEmpty());
    }
}
