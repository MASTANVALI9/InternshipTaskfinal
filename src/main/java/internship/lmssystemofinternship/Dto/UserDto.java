package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.user;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link user}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String username;
    String password;
    String role;
}