package internship.lmssystemofinternship.Service.Implentation;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.course;
import internship.lmssystemofinternship.Repository.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSerivce {
    private final CourseRepo courseRepo;
    private final ModelMapper modelMapper;
    private CourseDto convertToDTO(course courses) {
        return modelMapper.map(courses, CourseDto.class);
    }

    private course convertToEntity(CourseDto courseDTO) {
        return modelMapper.map(courseDTO, course.class);
    }

    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDto createCourse(CourseDto courseDTO) {
        course courses = convertToEntity(courseDTO);
        course saved = courseRepo. save(courses);
        return convertToDTO(saved);
    }
    public CourseDto getcourseByid(Long courseId) {
        return courseRepo.findById(courseId);
    }
}
