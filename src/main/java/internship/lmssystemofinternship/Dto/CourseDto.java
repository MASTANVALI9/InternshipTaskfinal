package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.Course;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Course}
 */
@Value
public class CourseDto implements Serializable {
    Long id;
    String title;
    String description;
    Long instructorId;
}