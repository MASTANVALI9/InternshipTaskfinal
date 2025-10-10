package internship.lmssystemofinternship.Config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.Progress;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import internship.lmssystemofinternship.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepo courseRepository;
    private final ProgressRepository progressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        if (userRepository.findByUsername("student").isEmpty()) {
            User student = new User();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("password"));
            student.setEmail("student@example.com");
            student.setRoles(Set.of(Roles.STUDENT));
            userRepository.save(student);
        }

        if (userRepository.findByUsername("instructor").isEmpty()) {
            User instructor = new User();
            instructor.setUsername("instructor");
            instructor.setPassword(passwordEncoder.encode("password"));
            instructor.setEmail("instructor@example.com");
            instructor.setRoles(Set.of(Roles.ADMIN)); // Use ADMIN instead of INSTRUCTOR for now
            userRepository.save(instructor);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setEmail("admin@example.com");
            admin.setRoles(Set.of(Roles.ADMIN));
            userRepository.save(admin);
        }

        // Create sample courses if they don't exist
        if (courseRepository.count() == 0) {
            User instructor = userRepository.findByUsername("instructor").orElse(null);
            if (instructor != null) {
                Course course1 = new Course();
                course1.setTitle("Java Programming Fundamentals");
                course1.setDescription("Learn the basics of Java programming");
                course1.setInstructor(instructor);
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setTitle("Spring Boot Development");
                course2.setDescription("Build web applications with Spring Boot");
                course2.setInstructor(instructor);
                courseRepository.save(course2);

                // Create sample progress for student
                User student = userRepository.findByUsername("student").orElse(null);
                if (student != null) {
                    Progress progress1 = new Progress();
                    progress1.setUser(student);
                    progress1.setCourse(course1);
                    progress1.setCompletionPercent(75);
                    progress1.setScore(85);
                    progressRepository.save(progress1);

                    Progress progress2 = new Progress();
                    progress2.setUser(student);
                    progress2.setCourse(course2);
                    progress2.setCompletionPercent(100);
                    progress2.setScore(92);
                    progressRepository.save(progress2);
                }
            }
        }
    }
}
