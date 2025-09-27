package internship.lmssystemofinternship.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long courseId; // Each quiz belongs to a course
}
