package internship.lmssystemofinternship.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    // Instructor who created the course
    private Long instructorId;
}
