package internship.lmssystemofinternship.Service.Implentation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.Progress;
import internship.lmssystemofinternship.Entity.Quiz;
import internship.lmssystemofinternship.Entity.QuizAttempt;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import internship.lmssystemofinternship.Repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    private User testUser;
    private Course testCourse;
    private Quiz testQuiz;
    private Progress testProgress;
    private QuizAttempt testQuizAttempt;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRoles(Set.of(Roles.STUDENT));

        testCourse = new Course();
        testCourse.setCourseId(1L);
        testCourse.setTitle("Test Course");

        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setTitle("Test Quiz");
        testQuiz.setCourse(testCourse);

        testProgress = new Progress();
        testProgress.setId(1L);
        testProgress.setCompletionPercent(75);
        testProgress.setScore(85);
        testProgress.setUser(testUser);
        testProgress.setCourse(testCourse);

        testQuizAttempt = new QuizAttempt();
        testQuizAttempt.setId(1L);
        testQuizAttempt.setScore(90);
    }

    @Test
    void getInstructorAnalytics_ShouldReturnAnalyticsMap() {
        // Arrange
        List<Progress> progressList = Arrays.asList(testProgress);
        when(progressRepository.findByCourse_CourseId(1L)).thenReturn(progressList);
        when(quizRepository.count()).thenReturn(5L);

        // Act
        Map<String, Object> result = analyticsService.getInstructorAnalytics(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.get("totalEnrollments"));
        assertEquals(5L, result.get("totalQuizzes"));
        assertEquals(75.0, result.get("averageCompletionRate"));
        assertEquals(85.0, result.get("averageQuizScore"));
        assertEquals(0L, result.get("completedStudents"));
        verify(progressRepository).findByCourse_CourseId(1L);
        verify(quizRepository).count();
    }

    @Test
    void getStudentAnalytics_ShouldReturnAnalyticsMap() {
        // Arrange
        List<Progress> progressList = Arrays.asList(testProgress);
        List<QuizAttempt> attempts = Arrays.asList(testQuizAttempt);
        when(progressRepository.findByUser_Id(1L)).thenReturn(progressList);

        // Act
        Map<String, Object> result = analyticsService.getStudentAnalytics(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.get("totalCoursesEnrolled"));
        assertEquals(0L, result.get("completedCourses"));
        assertEquals(0, result.get("totalQuizAttempts"));
        assertEquals(0.0, result.get("averageQuizScore"));
        assertNotNull(result.get("recommendedCourses"));
        verify(progressRepository).findByUser_Id(1L);
    }

    @Test
    void getSystemAnalytics_ShouldReturnSystemAnalyticsMap() {
        // Arrange
        List<Quiz> quizzes = Arrays.asList(testQuiz);
        List<Progress> progressList = Arrays.asList(testProgress);
        when(quizRepository.findAll()).thenReturn(quizzes);
        when(progressRepository.findAll()).thenReturn(progressList);

        // Act
        Map<String, Object> result = analyticsService.getSystemAnalytics();

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.get("totalCourses"));
        assertEquals(1L, result.get("totalUsers"));
        assertEquals(1L, result.get("totalQuizzes"));
        verify(quizRepository).findAll();
        verify(progressRepository).findAll();
    }

    @Test
    void getInstructorAnalytics_WithCompletedStudents_ShouldReturnCorrectCount() {
        // Arrange
        Progress completedProgress = new Progress();
        completedProgress.setCompletionPercent(100);
        completedProgress.setScore(95);

        List<Progress> progressList = Arrays.asList(testProgress, completedProgress);
        when(progressRepository.findByCourse_CourseId(1L)).thenReturn(progressList);
        when(quizRepository.count()).thenReturn(3L);

        // Act
        Map<String, Object> result = analyticsService.getInstructorAnalytics(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.get("totalEnrollments"));
        assertEquals(1L, result.get("completedStudents"));
        assertEquals(87.5, result.get("averageCompletionRate")); // (75 + 100) / 2
        assertEquals(90.0, result.get("averageQuizScore")); // (85 + 95) / 2
    }

    @Test
    void getStudentAnalytics_WithHighScore_ShouldReturnAdvancedRecommendations() {
        // Arrange
        List<Progress> progressList = Arrays.asList(testProgress);
        List<QuizAttempt> highScoreAttempts = Arrays.asList(
            new QuizAttempt() {{ setScore(85); }},
            new QuizAttempt() {{ setScore(90); }},
            new QuizAttempt() {{ setScore(88); }}
        );
        when(progressRepository.findByUser_Id(1L)).thenReturn(progressList);

        // Act
        Map<String, Object> result = analyticsService.getStudentAnalytics(1L);

        // Assert
        assertNotNull(result);
        @SuppressWarnings("unchecked")
        List<String> recommendations = (List<String>) result.get("recommendedCourses");
        assertNotNull(recommendations);
        assertEquals(3, recommendations.size());
        assertTrue(recommendations.contains("Advanced topics"));
    }
}
