package internship.lmssystemofinternship.Controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.lmssystemofinternship.Dto.QuizDto;
import internship.lmssystemofinternship.Service.Implentation.QuizService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        QuizDto quizDto = quizService.getQuizById(id);
        if (quizDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDto);
    }
    @PostMapping
    public ResponseEntity<QuizDto> addQuiz(@RequestBody QuizDto quizDto) {
        QuizDto savedQuiz = quizService.addQuiz(quizDto);
        return ResponseEntity.ok(savedQuiz);
    }
}
