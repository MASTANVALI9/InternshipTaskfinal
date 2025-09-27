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
}
