package internship.lmssystemofinternship.Entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;  // camelCase

    private String title;
    private String description;
    // private String instructorName;  // removed, use instructor relation instead

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)  // assuming Quiz has field 'course'
    private List<Quiz> quizzes;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<User> enrolledStudents;

}

