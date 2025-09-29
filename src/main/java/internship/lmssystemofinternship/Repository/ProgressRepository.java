package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Entity.progress;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface ProgressRepository extends Repository<progress, Long> {
    Collection<Object> findByUserId(Long userId);

    Collection<Object> findByCourseId(Long courseId);

    ModelMapper findById(Long id);
}