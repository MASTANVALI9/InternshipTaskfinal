package internship.lmssystemofinternship.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.lmssystemofinternship.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA provides findById and findAll, so custom definitions are not needed.
    // They are available through the JpaRepository interface.

    // A more common practice is to return Optional for single results to handle cases where a user might not be found.
    Optional<User> findByEmail(String email);

    // This method name assumes there's a property named 'courseId' in the User entity.
    // If a User is linked to a Course through a collection, the method should be findByCourses_Id.
   List<User> findByEmailContaining(String email);


    // Use Optional for a single result that might not exist.
    





    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String username);

    // Method to find first user by username (handles duplicates)
    default Optional<User> findFirstByUsername(String username) {
        return findByUsername(username).stream().findFirst();
    }
}
