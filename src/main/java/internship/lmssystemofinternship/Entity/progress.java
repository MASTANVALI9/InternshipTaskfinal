package internship.lmssystemofinternship.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
@Table(name = "progress")
public class progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long courseId;
    private int completionPercent;
    private int score;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private user student;

    // Course being tracked
    @ManyToOne
    @JoinColumn(name = "course_id")
    private course courses;
}
