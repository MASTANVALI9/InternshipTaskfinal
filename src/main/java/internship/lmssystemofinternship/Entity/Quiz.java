package internship.lmssystemofinternship.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long courseId;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private course courses;

    // One Quiz has many Questions
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    // Each quiz belongs to a course
}
