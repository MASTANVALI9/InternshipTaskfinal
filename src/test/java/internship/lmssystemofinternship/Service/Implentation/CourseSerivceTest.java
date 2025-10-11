package internship.lmssystemofinternship.Service.Implentation;

import java.util.ArrayList;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CourseSerivceTest {

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseSerivce courseService;

    private Course testCourse;
    private CourseDto testCourseDto;
    private User testInstructor;
    private User testStudent;

    @BeforeEach
    void setUp() {
        testInstructor = new User();
        testInstructor.setId(1L);
        testInstructor.setUsername("instructor1");
        testInstructor.setRoles(Set.of(Roles.ADMIN));

        testStudent = new User();
        testStudent.setId(2L);
        testStudent.setUsername("student1");
        testStudent.setRoles(Set.of(Roles.STUDENT));

        testCourse = new Course();
        testCourse.setCourseId(1L);
        testCourse.setTitle("Test Course");
        testCourse.setDescription("Test Description");
        testCourse.setInstructor(testInstructor);
        testCourse.setEnrolledStudents(new ArrayList<>());

        testCourseDto = new CourseDto(1L, "Test Course", "Test Description", 1L);
    }

    @Test
    void getAllCourses_ShouldReturnListOfCourseDtos() {
        // Arrange
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findAll()).thenReturn(courses);
        when(modelMapper.map(testCourse, CourseDto.class)).thenReturn(testCourseDto);

        // Act
        List<CourseDto> result = courseService.getAllCourses();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Course", result.get(0).getTitle());
        verify(courseRepo).findAll();
    }

    @Test
    void createCourse_ShouldSaveAndReturnCourseDto() {
        // Arrange
        when(modelMapper.map(testCourseDto, Course.class)).thenReturn(testCourse);
        when(courseRepo.save(testCourse)).thenReturn(testCourse);
        when(modelMapper.map(testCourse, CourseDto.class)).thenReturn(testCourseDto);

        // Act
        CourseDto result = courseService.createCourse(testCourseDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Course", result.getTitle());
        verify(courseRepo).save(testCourse);
    }

    @Test
    void getcourseByid_WhenCourseExists_ShouldReturnCourseDto() {
        // Arrange
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        when(modelMapper.map(testCourse, CourseDto.class)).thenReturn(testCourseDto);

        // Act
        CourseDto result = courseService.getcourseByid(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Course", result.getTitle());
        verify(courseRepo).findById(1L);
    }

    @Test
    void updateCourse_ShouldUpdateAndReturnCourseDto() {
        // Arrange
        CourseDto updatedDto = new CourseDto(1L, "Updated Course", "Updated Description", 1L);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        when(courseRepo.save(testCourse)).thenReturn(testCourse);
        when(modelMapper.map(testCourse, CourseDto.class)).thenReturn(updatedDto);

        // Act
        CourseDto result = courseService.updateCourse(1L, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Course", result.getTitle());
        verify(courseRepo).findById(1L);
        verify(courseRepo).save(testCourse);
    }

    @Test
    void deleteCourse_ShouldCallDeleteById() {
        // Arrange
        doNothing().when(courseRepo).deleteById(1L);

        // Act
        courseService.deleteCourse(1L);

        // Assert
        verify(courseRepo).deleteById(1L);
    }

    @Test
    void enrollStudent_ShouldAddStudentToCourse() {
        // Arrange
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        when(userRepository.findById(2L)).thenReturn(Optional.of(testStudent));
        when(courseRepo.save(testCourse)).thenReturn(testCourse);
        when(userRepository.save(testStudent)).thenReturn(testStudent);

        // Act
        courseService.enrollStudent(1L, 2L);

        // Assert
        verify(courseRepo).findById(1L);
        verify(userRepository).findById(2L);
        verify(courseRepo).save(testCourse);
        verify(userRepository).save(testStudent);
    }
}
