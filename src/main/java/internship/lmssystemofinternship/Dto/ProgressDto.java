package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.progress;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link progress}
 */
@Data
public class ProgressDto implements Serializable {
    Long id;
    Long studentId;
    Long courseId;
    int completionPercent;
    int score;
}