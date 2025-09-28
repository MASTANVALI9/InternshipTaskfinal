package internship.lmssystemofinternship.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // ADMIN, INSTRUCTOR, STUDENT

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<course> courses;
}
