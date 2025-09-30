package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Entity.Question;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends Repository<Question, Long> {
    List<Question> findAll();

    Optional<Question> findById(Long id);

    Question save(Question question);

    void deleteById(Long id);
}