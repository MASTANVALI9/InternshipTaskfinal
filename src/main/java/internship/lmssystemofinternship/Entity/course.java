package internship.lmssystemofinternship.Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    // Instructor who created the course
    private Long instructorId;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private user instructor;

    // One Course has many Quizzes
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;
}
