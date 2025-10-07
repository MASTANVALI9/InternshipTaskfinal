package internship.lmssystemofinternship.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "progress")
@Getter
@Setter
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int completionPercent;
    private int score;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}




