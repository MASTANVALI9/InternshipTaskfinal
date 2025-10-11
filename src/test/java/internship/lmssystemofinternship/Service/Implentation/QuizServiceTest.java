package internship.lmssystemofinternship.Service.Implentation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import internship.lmssystemofinternship.Dto.QuizDto;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.Quiz;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private QuizService quizService;

    private Quiz testQuiz;
    private QuizDto testQuizDto;
    private Course testCourse;
    private User testInstructor;

    @BeforeEach
    void setUp() {
        testInstructor = new User();
        testInstructor.setId(1L);
        testInstructor.setUsername("instructor1");
        testInstructor.setRoles(Set.of(Roles.ADMIN));

        testCourse = new Course();
        testCourse.setCourseId(1L);
        testCourse.setTitle("Test Course");
        testCourse.setInstructor(testInstructor);

        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setTitle("Test Quiz");
        testQuiz.setCourse(testCourse);

        testQuizDto = new QuizDto();
        testQuizDto.setId(1L);
        testQuizDto.setTitle("Test Quiz");
        testQuizDto.setCourseId(1L);
    }

    @Test
    void getAllQuizzes_ShouldReturnListOfQuizDtos() {
        // Arrange
        List<Quiz> quizzes = Arrays.asList(testQuiz);
        when(quizRepository.findAll()).thenReturn(quizzes);
        when(modelMapper.map(testQuiz, QuizDto.class)).thenReturn(testQuizDto);

        // Act
        List<QuizDto> result = quizService.getAllQuizzes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Quiz", result.get(0).getTitle());
        verify(quizRepository).findAll();
    }

    @Test
    void addQuiz_ShouldSaveAndReturnQuizDto() {
        // Arrange
        when(modelMapper.map(testQuizDto, Quiz.class)).thenReturn(testQuiz);
        when(quizRepository.save(testQuiz)).thenReturn(testQuiz);
        when(modelMapper.map(testQuiz, QuizDto.class)).thenReturn(testQuizDto);

        // Act
        QuizDto result = quizService.addQuiz(testQuizDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Quiz", result.getTitle());
        verify(quizRepository).save(testQuiz);
    }

    @Test
    void getQuizById_WhenQuizExists_ShouldReturnQuizDto() {
        // Arrange
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
        when(modelMapper.map(testQuiz, QuizDto.class)).thenReturn(testQuizDto);

        // Act
        QuizDto result = quizService.getQuizById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Quiz", result.getTitle());
        verify(quizRepository).findById(1L);
    }
}
