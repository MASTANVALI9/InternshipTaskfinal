package internship.lmssystemofinternship.Integration;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.UserRepository;

@SpringBootTest
@AutoConfigureWebMvc
@Transactional
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class FullStackIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepo courseRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private User testUser;
    private Course testCourse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();

        // Clean up database
        courseRepository.deleteAll();
        userRepository.deleteAll();

        // Create test user
        testUser = new User();
        testUser.setUsername("integrationtest");
        testUser.setEmail("integration@test.com");
        testUser.setPassword("password123");
        testUser.setRoles(Set.of(Roles.ADMIN));
        testUser = userRepository.save(testUser);

        // Create test course
        testCourse = new Course();
        testCourse.setTitle("Integration Test Course");
        testCourse.setDescription("Testing course creation");
        testCourse.setInstructor(testUser);
        testCourse = courseRepository.save(testCourse);
    }

    @Test
    @WithMockUser(username = "integrationtest", roles = "ADMIN")
    void fullWorkflow_ShouldCreateAndRetrieveCourse() throws Exception {
        // Test course creation via REST API
        CourseDto newCourseDto = new CourseDto(
            null,
            "New Integration Course",
            "Course created through integration test",
            testUser.getId()
        );

        String response = mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Integration Course"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify course was saved to database
        Long courseCount = courseRepository.count();
        assert courseCount == 2; // Original + new course

        // Test retrieving all courses
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "integrationtest", roles = "ADMIN")
    void userProfile_ShouldReturnUserData() throws Exception {
        mockMvc.perform(get("/api/users/profile/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationtest"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @WithMockUser(username = "integrationtest", roles = "ADMIN")
    void courseById_ShouldReturnCorrectCourse() throws Exception {
        mockMvc.perform(get("/api/courses/" + testCourse.getCourseId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Course"))
                .andExpect(jsonPath("$.description").value("Testing course creation"));
    }

    @Test
    @WithMockUser(username = "integrationtest", roles = "ADMIN")
    void coursesByInstructor_ShouldReturnInstructorsCourses() throws Exception {
        mockMvc.perform(get("/api/courses/instructor/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Integration Test Course"));
    }
}
