package internship.lmssystemofinternship.Service.Implentation;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseSerivce {
    public CourseDto updateCourse(Long courseId, CourseDto courseDTO) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        // Optionally update instructor and other fields
        Course saved = courseRepo.save(course);
        return convertToDTO(saved);
    }

    public void deleteCourse(Long courseId) {
        courseRepo.deleteById(courseId);
    }

    private final UserRepository userRepository;

    public void enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        if (!course.getEnrolledStudents().contains(student)) {
            course.getEnrolledStudents().add(student);
        }
        if (!student.getEnrolledCourses().contains(course)) {
            student.getEnrolledCourses().add(course);
        }
        courseRepo.save(course);
        userRepository.save(student);
    }
    private final CourseRepo courseRepo;
    private final ModelMapper modelMapper;
    private CourseDto convertToDTO(Course courses) {
        return modelMapper.map(courses, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }

    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDto createCourse(CourseDto courseDTO) {
        Course courses = convertToEntity(courseDTO);
        Course saved = courseRepo. save(courses);
        return convertToDTO(saved);
    }
    public CourseDto getcourseByid(Long courseId) {
        return courseRepo.findById(courseId)
                .map(this::convertToDTO)
                .orElseThrow(null);
    }


}
