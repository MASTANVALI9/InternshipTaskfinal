package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.course;
import org.springframework.data.repository.Repository;

import java.util.List;
public interface CourseRepo extends Repository<course, Long> {
    List<CourseDto> findAll();

    course save(course courses);

    CourseDto findById(Long courseId);
}