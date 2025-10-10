package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @BeforeAll
    static void beforeAll() {
        System.out.println("testing jpa");
    }
    @Test

    void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setUsername("mastan");
        user.setEmail("mastan@gmail.com");
        user.setPassword("12345");
        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("mastan@gmail.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("mastan", found.get().getUsername());
    }

    @Test
    void testFindByEmailContaining() {
        User user1 = new User();
        user1.setUsername("vali");
        user1.setEmail("vali@gmail.com");
        user1.setPassword("abc");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("shaik");
        user2.setEmail("shaik@yahoo.com");
        user2.setPassword("xyz");
        userRepository.save(user2);

        List<User> found = userRepository.findByEmailContaining("gmail");

        assertEquals(1, found.size());
        assertEquals("vali@gmail.com", found.get(0).getEmail());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@user.com");
        user.setPassword("pass");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testUser");

        assertTrue(found.isPresent());
        assertEquals("test@user.com", found.get().getEmail());
    }

    @Test
    void testFindByUsernameIgnoreCase() {
        User user = new User();
        user.setUsername("AdminUser");
        user.setEmail("admin@xyz.com");
        user.setPassword("pass");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsernameIgnoreCase("adminuser");

        assertTrue(found.isPresent());
        assertEquals("AdminUser", found.get().getUsername());
    }

    @Test
    void testDeleteInBatch() {
        User user1 = new User();
        user1.setUsername("john");
        user1.setEmail("john@abc.com");
        user1.setPassword("123");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("mark");
        user2.setEmail("mark@abc.com");
        user2.setPassword("456");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());

        userRepository.deleteInBatch(users);

        List<User> afterDelete = userRepository.findAll();
        assertTrue(afterDelete.isEmpty());
    }
}
