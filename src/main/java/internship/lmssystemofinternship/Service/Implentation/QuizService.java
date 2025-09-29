package internship.lmssystemofinternship.Service.Implentation;

import internship.lmssystemofinternship.Dto.QuizDto;
import internship.lmssystemofinternship.Entity.Quiz;
import internship.lmssystemofinternship.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll();
    }
    public QuizDto getQuizById(Long id) {
        return quizRepository.findById(id);

    }
    private  Quiz convertToEntity(QuizDto quizDto) {
        return modelMapper.map(quizDto, Quiz.class);
    }
    private QuizDto convertToDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }
    public QuizDto AddQQuiz(QuizDto quizDto) {
        Quiz quiz = convertToEntity(quizDto)
        Quiz saved = quizRepository.save(quiz);
        return convertToDto(saved);

    }
}
