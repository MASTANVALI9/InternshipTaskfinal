package internship.lmssystemofinternship.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // ADMIN, INSTRUCTOR, STUDENT
}
