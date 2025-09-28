package internship.lmssystemofinternship.Dto;

import internship.lmssystemofinternship.Entity.Question;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Question}
 */
@Data
public class QuestionDto implements Serializable {
    Long id;
    Long quizId;
    String questionText;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctAnswer;
}