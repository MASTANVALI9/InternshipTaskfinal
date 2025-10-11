package internship.lmssystemofinternship.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.lmssystemofinternship.Entity.Course;
import internship.lmssystemofinternship.Entity.Forum;
import internship.lmssystemofinternship.Repository.CourseRepo;
import internship.lmssystemofinternship.Repository.ForumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false"
})
class ForumIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private CourseRepo courseRepo;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Forum forum;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.objectMapper = new ObjectMapper();

        forumRepository.deleteAll();
        courseRepo.deleteAll();

        forum = new Forum();
        forum.setTitle("Integration Forum");
        forum.setDescription("Forum for integration tests");
        forum.setActive(true);
        forum = forumRepository.save(forum);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllForums_shouldReturnArray() throws Exception {
        mockMvc.perform(get("/api/forums/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Integration Forum"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_existing_shouldReturnForum() throws Exception {
        mockMvc.perform(get("/api/forums/" + forum.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(forum.getId()))
                .andExpect(jsonPath("$.title").value("Integration Forum"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_notFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/forums/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createForum_shouldPersist_andReturnEntity() throws Exception {
        Forum payload = new Forum();
        payload.setTitle("Created via API");
        payload.setDescription("Created desc");

        mockMvc.perform(post("/api/forums/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Created via API"))
                .andExpect(jsonPath("$.description").value("Created desc"));

        List<Forum> all = forumRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateForum_existing_shouldReturnUpdated() throws Exception {
        Forum update = new Forum();
        update.setTitle("Updated via API");
        update.setDescription("Updated desc");

        mockMvc.perform(put("/api/forums/update/" + forum.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(forum.getId()))
                .andExpect(jsonPath("$.title").value("Updated via API"))
                .andExpect(jsonPath("$.description").value("Updated desc"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateForum_notFound_shouldReturn404() throws Exception {
        Forum update = new Forum();
        update.setTitle("Updated via API");
        update.setDescription("Updated desc");

        mockMvc.perform(put("/api/forums/update/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteForum_existing_shouldReturn200_andRemove() throws Exception {
        mockMvc.perform(delete("/api/forums/delete/" + forum.getId()))
                .andExpect(status().isOk());

        assertThat(forumRepository.findById(forum.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteForum_notFound_shouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/forums/delete/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumByCourse_existing_shouldReturnForum() throws Exception {
        // Prepare course and forum relation
        Course c = new Course();
        c.setTitle("T1");
        c.setDescription("D1");
        c = courseRepo.save(c);

        Forum attached = new Forum();
        attached.setTitle("ByCourse");
        attached.setDescription("Desc");
        attached.setCourse(c);
        attached.setActive(true);
        attached = forumRepository.save(attached);

        mockMvc.perform(get("/api/forums/course/" + c.getCourseId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(attached.getId()))
                .andExpect(jsonPath("$.title").value("ByCourse"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumByCourse_notFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/forums/course/123456"))
                .andExpect(status().isNotFound());
    }
}
