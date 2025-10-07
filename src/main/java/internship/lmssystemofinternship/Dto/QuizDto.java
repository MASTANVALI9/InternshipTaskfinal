package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.Quiz;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Quiz}
 */
@Data
public class QuizDto implements Serializable {
    private Long id;
    String title;
    Long courseId;
 }