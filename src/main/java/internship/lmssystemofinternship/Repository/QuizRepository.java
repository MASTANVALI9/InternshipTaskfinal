package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Dto.QuizDto;
import internship.lmssystemofinternship.Entity.Quiz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface QuizRepository extends Repository<Quiz, Long> {
    List<QuizDto> findAll();

    QuizDto findById(Long id);

    Quiz save(Quiz quiz);
}