package internship.lmssystemofinternship.Config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.Progress;
import internship.lmssystemofinternship.Entity.Quiz;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import internship.lmssystemofinternship.Repository.QuizRepository;
import internship.lmssystemofinternship.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepo courseRepository;
    private final ProgressRepository progressRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin users if they don't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@lms.com");
            admin.setRoles(Set.of(Roles.ADMIN));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("masteradmin").isEmpty()) {
            User masterAdmin = new User();
            masterAdmin.setUsername("masteradmin");
            masterAdmin.setPassword(passwordEncoder.encode("admin123"));
            masterAdmin.setEmail("masteradmin@lms.com");
            masterAdmin.setRoles(Set.of(Roles.ADMIN));
            userRepository.save(masterAdmin);
        }

        // Create teacher users if they don't exist
        String[] teacherUsernames = {"teacher1", "teacher2", "dr_smith", "prof_johnson"};
        for (String teacherUsername : teacherUsernames) {
            if (userRepository.findByUsername(teacherUsername).isEmpty()) {
                User teacher = new User();
                teacher.setUsername(teacherUsername);
                teacher.setPassword(passwordEncoder.encode("teacher123"));
                teacher.setEmail(teacherUsername + "@lms.com");
                teacher.setRoles(Set.of(Roles.ADMIN));
                userRepository.save(teacher);
            }
        }

        // Create 10 student users if they don't exist
        String[][] students = {
            {"student1", "john.doe@student.com"},
            {"student2", "jane.smith@student.com"},
            {"student3", "mike.johnson@student.com"},
            {"student4", "sarah.williams@student.com"},
            {"student5", "david.brown@student.com"},
            {"student6", "emily.davis@student.com"},
            {"student7", "chris.miller@student.com"},
            {"student8", "lisa.wilson@student.com"},
            {"student9", "kevin.moore@student.com"},
            {"student10", "rachel.taylor@student.com"}
        };

        for (String[] studentInfo : students) {
            if (userRepository.findByUsername(studentInfo[0]).isEmpty()) {
                User student = new User();
                student.setUsername(studentInfo[0]);
                student.setPassword(passwordEncoder.encode("student123"));
                student.setEmail(studentInfo[1]);
                student.setRoles(Set.of(Roles.STUDENT));
                userRepository.save(student);
            }
        }

        // Create sample courses if they don't exist
        if (courseRepository.count() == 0) {
            User teacher1 = userRepository.findByUsername("teacher1").orElse(null);
            User teacher2 = userRepository.findByUsername("teacher2").orElse(null);

            if (teacher1 != null) {
                Course course1 = new Course();
                course1.setTitle("Java Programming Fundamentals");
                course1.setDescription("Learn the basics of Java programming including variables, loops, conditionals, and object-oriented programming concepts.");
                course1.setInstructor(teacher1);
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setTitle("Spring Boot Development");
                course2.setDescription("Build modern web applications with Spring Boot framework, including REST APIs, database integration, and security.");
                course2.setInstructor(teacher1);
                courseRepository.save(course2);

                Course course3 = new Course();
                course3.setTitle("Database Design and SQL");
                course3.setDescription("Learn database design principles, normalization, and advanced SQL queries for data management.");
                course3.setInstructor(teacher1);
                courseRepository.save(course3);
            }

            if (teacher2 != null) {
                Course course4 = new Course();
                course4.setTitle("Web Development with HTML/CSS/JavaScript");
                course4.setDescription("Create responsive and interactive websites using modern HTML5, CSS3, and JavaScript ES6+ features.");
                course4.setInstructor(teacher2);
                courseRepository.save(course4);

                Course course5 = new Course();
                course5.setTitle("React.js Frontend Development");
                course5.setDescription("Build dynamic user interfaces with React.js, including hooks, context API, and state management.");
                course5.setInstructor(teacher2);
                courseRepository.save(course5);

                Course course6 = new Course();
                course6.setTitle("Python for Data Science");
                course6.setDescription("Use Python for data analysis, visualization, and machine learning with libraries like Pandas, NumPy, and Scikit-learn.");
                course6.setInstructor(teacher2);
                courseRepository.save(course6);
            }
        }

        // Create sample quizzes if they don't exist
        if (quizRepository.count() == 0) {
            var courses = courseRepository.findAll();

            for (Course course : courses) {
                Quiz quiz = new Quiz();
                quiz.setTitle(course.getTitle() + " Quiz");
                quiz.setCourse(course);
                quizRepository.save(quiz);
            }
        }

        // Create sample progress data for all students if it doesn't exist
        if (progressRepository.count() == 0) {
            // Get all courses
            var courses = courseRepository.findAll();

            // Create progress for each student in each course
            for (String[] studentInfo : students) {
                User student = userRepository.findByUsername(studentInfo[0]).orElse(null);
                if (student != null) {
                    for (Course course : courses) {
                        // Check if progress already exists for this student-course combination
                        boolean progressExists = progressRepository.findAll().stream()
                            .anyMatch(p -> p.getUser().getId().equals(student.getId()) &&
                                          p.getCourse().getCourseId().equals(course.getCourseId()));

                        if (!progressExists) {
                            Progress progress = new Progress();
                            progress.setUser(student);
                            progress.setCourse(course);

                            // Generate random but realistic progress data
                            int completionPercent = (int) (Math.random() * 100);
                            progress.setCompletionPercent(completionPercent);
                            progress.setScore(completionPercent > 0 ? (int) (Math.random() * 40 + 60) : 0); // Score between 60-100 if started
                            progressRepository.save(progress);
                        }
                    }
                }
            }
        }

        // Print summary of created data
        System.out.println("=== LMS Sample Data Initialized ===");
        System.out.println("Total Users: " + userRepository.count());
        System.out.println("Total Courses: " + courseRepository.count());
        System.out.println("Total Progress Records: " + progressRepository.count());
        System.out.println("Total Quizzes: " + quizRepository.count());
        System.out.println("==================================");

        // Print login credentials
        System.out.println("=== Login Credentials ===");
        System.out.println("Admin: admin / admin123");
        System.out.println("Master Admin: masteradmin / admin123");
        System.out.println("Teachers: teacher1, teacher2, dr_smith, prof_johnson / teacher123");
        System.out.println("Students: student1-10 / student123");
        System.out.println("========================");
    }
}
