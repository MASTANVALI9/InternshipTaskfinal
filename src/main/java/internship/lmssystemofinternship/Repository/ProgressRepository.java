package internship.lmssystemofinternship.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import internship.lmssystemofinternship.Entity.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUser_Id(Long userId);
    List<Progress> findByCourse_CourseId(Long courseId);
}