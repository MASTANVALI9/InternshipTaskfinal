package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.Progress;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Progress}
 */
@Data
public class ProgressDto implements Serializable {
    Long id;
    Long studentId;
    Long courseId;
    int completionPercent;
    int score;
}