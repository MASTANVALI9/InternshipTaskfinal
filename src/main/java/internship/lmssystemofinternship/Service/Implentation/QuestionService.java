package internship.lmssystemofinternship.Service.Implentation;


import internship.lmssystemofinternship.Dto.QuestionDto;
import internship.lmssystemofinternship.Entity.Question;
import internship.lmssystemofinternship.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            questionDtos.add(convertToDto(question));
        }
        return questionDtos;
    }

    public QuestionDto getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.map(this::convertToDto).orElse(null);
    }

    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question question = convertToEntity(questionDto);
        Question saved = questionRepository.save(question);
        return convertToDto(saved);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private Question convertToEntity(QuestionDto dto) {
        return modelMapper.map(dto, Question.class);
    }

    private QuestionDto convertToDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }
}

