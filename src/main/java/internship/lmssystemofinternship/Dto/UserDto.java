package internship.lmssystemofinternship.Dto;


import lombok.Data;
import lombok.Value;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String username;
    String password;
    String role;
}