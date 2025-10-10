package internship.lmssystemofinternship.Service.Implentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import internship.lmssystemofinternship.Entity.Progress;
import internship.lmssystemofinternship.Entity.QuizAttempt;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import internship.lmssystemofinternship.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ProgressRepository progressRepository;
    private final QuizRepository quizRepository;

    /**
     * Get analytics for instructor dashboard
     */
    public Map<String, Object> getInstructorAnalytics(Long courseId) {
        Map<String, Object> analytics = new HashMap<>();

        // Enrollment statistics
        List<Progress> courseProgress = progressRepository.findByCourse_CourseId(courseId);
        analytics.put("totalEnrollments", courseProgress.size());

        // Quiz performance - simplified for now as QuizRepository doesn't have course query yet
        long totalQuizzes = quizRepository.count();
        analytics.put("totalQuizzes", totalQuizzes);

        double averageCompletion = courseProgress.stream()
            .mapToInt(Progress::getCompletionPercent)
            .average().orElse(0.0);
        analytics.put("averageCompletionRate", averageCompletion);

        double averageQuizScore = courseProgress.stream()
            .mapToInt(Progress::getScore)
            .average().orElse(0.0);
        analytics.put("averageQuizScore", averageQuizScore);

        // Completion categories
        long completedStudents = courseProgress.stream()
            .filter(p -> p.getCompletionPercent() == 100)
            .count();
        analytics.put("completedStudents", completedStudents);

        return analytics;
    }

    /**
     * Get student-specific analytics
     */
    public Map<String, Object> getStudentAnalytics(Long studentId) {
        Map<String, Object> analytics = new HashMap<>();

        List<Progress> studentProgress = progressRepository.findByUser_Id(studentId);
        analytics.put("totalCoursesEnrolled", studentProgress.size());

        long completedCourses = studentProgress.stream()
            .filter(p -> p.getCompletionPercent() == 100)
            .count();
        analytics.put("completedCourses", completedCourses);

        // Quiz performance history - simplified for now as QuizAttemptRepository not fully implemented
        List<QuizAttempt> attempts = List.of(); // TODO: Implement proper quiz tracking
        analytics.put("totalQuizAttempts", attempts.size());

        double averageScore = attempts.stream()
            .mapToInt(QuizAttempt::getScore)
            .average().orElse(0.0);
        analytics.put("averageQuizScore", averageScore);

        // Generate learning recommendations
        analytics.put("recommendedCourses", generateRecommendations(studentId, attempts));

        return analytics;
    }

    /**
     * AI-powered course recommendations based on performance
     */
    private List<String> generateRecommendations(Long studentId, List<QuizAttempt> attempts) {
        // Simple recommendation logic based on average performance
        double avgScore = attempts.stream().mapToInt(QuizAttempt::getScore).average().orElse(0.0);

        if (avgScore >= 80) {
            return List.of("Advanced topics", "Specialization courses", "Project-based learning");
        } else if (avgScore >= 60) {
            return List.of("Intermediate courses", "Practice quizzes", "Study groups");
        } else {
            return List.of("Foundation courses", "Basic tutorials", "One-on-one mentoring");
        }
    }

    /**
     * Get overall system analytics
     */
    public Map<String, Object> getSystemAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        // Course and user statistics
        long totalCourses = quizRepository.findAll().stream().map(q -> q.getCourse().getCourseId()).distinct().count();
        analytics.put("totalCourses", totalCourses);

        long totalUsers = progressRepository.findAll().stream().map(p -> p.getUser().getId()).distinct().count();
        analytics.put("totalUsers", totalUsers);

        long totalQuizzes = quizRepository.count();
        analytics.put("totalQuizzes", totalQuizzes);

        return analytics;
    }
}
