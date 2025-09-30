package internship.lmssystemofinternship.Controller;
import internship.lmssystemofinternship.Dto.QuestionDto;
import internship.lmssystemofinternship.Service.Implentation.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.getAllQuestions();
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }


    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        QuestionDto questionDto = questionService.getQuestionById(id);
        if (questionDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionDto);
    }

    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionDto questionDto) {
        QuestionDto saved = questionService.addQuestion(questionDto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
