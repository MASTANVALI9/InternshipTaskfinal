package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.course;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link course}
 */
@Value
public class CourseDto implements Serializable {
    Long id;
    String title;
    String description;
    Long instructorId;
}