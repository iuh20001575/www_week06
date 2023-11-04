package vn.edu.iuh.fit;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;

import java.time.Instant;
import java.util.List;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ANNOTATED)
class WwwWeek06ApplicationTests {
    @Autowired
    private UserRepository userRepository;

//    @PostConstruct
    void insert() {
        User user;
        for (int i = 0; i < 100; i++) {
            user = new User(
                    LoremIpsum.getInstance().getFirstName(),
                    LoremIpsum.getInstance().getLastName(),
                    LoremIpsum.getInstance().getLastName(),
                    LoremIpsum.getInstance().getPhone(),
                    LoremIpsum.getInstance().getEmail(),
                    BCrypt.withDefaults().hashToString(23, LoremIpsum.getInstance().getPhone().toCharArray()),
                    Instant.now(), null, LoremIpsum.getInstance().getTitle(100),
                    LoremIpsum.getInstance().getWords(1000)
            );

            userRepository.save(user);
        }
    }

    @Test
    void contextLoads() {
        List<User> users = userRepository.findAll();

        Assertions.assertFalse(users.isEmpty());
    }

}
