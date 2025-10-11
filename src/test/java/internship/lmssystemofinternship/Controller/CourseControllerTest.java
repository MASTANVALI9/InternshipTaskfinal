package internship.lmssystemofinternship.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Service.Implentation.CourseSerivce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseSerivce courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course testCourse;
    private CourseDto testCourseDto;
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
        testCourse.setDescription("Test Description");
        testCourse.setInstructor(testInstructor);

        testCourseDto = new CourseDto(1L, "Test Course", "Test Description", 1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllCourses_ShouldReturnListOfCourses() throws Exception {
        // Arrange
        List<CourseDto> courses = Arrays.asList(testCourseDto);
        when(courseService.getAllCourses()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Course"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getCourseById_WhenCourseExists_ShouldReturnCourse() throws Exception {
        // Arrange
        when(courseService.getcourseByid(1L)).thenReturn(testCourseDto);

        // Act & Assert
        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Course"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        // Arrange
        when(courseService.createCourse(testCourseDto)).thenReturn(testCourseDto);

        // Act & Assert
        mockMvc.perform(post("/api/courses/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCourseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Course"));
    }
}
