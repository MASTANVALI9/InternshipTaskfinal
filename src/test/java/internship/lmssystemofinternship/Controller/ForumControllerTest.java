package internship.lmssystemofinternship.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.lmssystemofinternship.Entity.Forum;
import internship.lmssystemofinternship.Service.Implentation.ForumService;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForumController.class)
class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumService forumService;

    @Autowired
    private ObjectMapper objectMapper;

    private Forum testForum;
    private Forum updatedForum;

    @BeforeEach
    void setUp() {
        testForum = new Forum();
        testForum.setId(1L);
        testForum.setTitle("General Discussion");
        testForum.setDescription("A forum for general topics");
        testForum.setActive(true);

        updatedForum = new Forum();
        updatedForum.setId(1L);
        updatedForum.setTitle("Updated Title");
        updatedForum.setDescription("Updated Description");
        updatedForum.setActive(true);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllForums_ShouldReturnListOfForums() throws Exception {
        // Arrange
        List<Forum> forums = Arrays.asList(testForum);
        when(forumService.getAllForums()).thenReturn(forums);

        // Act & Assert
        mockMvc.perform(get("/api/forums/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("General Discussion"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumById_WhenExists_ShouldReturnForum() throws Exception {
        // Arrange
        when(forumService.getForumById(1L)).thenReturn(testForum);

        // Act & Assert
        mockMvc.perform(get("/api/forums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("General Discussion"))
                .andExpect(jsonPath("$.description").value("A forum for general topics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumById_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        when(forumService.getForumById(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/forums/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumByCourse_WhenExists_ShouldReturnForum() throws Exception {
        // Arrange
        when(forumService.getForumByCourse(10L)).thenReturn(testForum);

        // Act & Assert
        mockMvc.perform(get("/api/forums/course/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("General Discussion"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getForumByCourse_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        when(forumService.getForumByCourse(10L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/forums/course/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createForum_ShouldReturnCreatedForum() throws Exception {
        // Arrange
        when(forumService.createForum(testForum)).thenReturn(testForum);

        // Act & Assert
        mockMvc.perform(post("/api/forums/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testForum)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("General Discussion"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateForum_WhenExists_ShouldReturnUpdatedForum() throws Exception {
        // Arrange
        when(forumService.updateForum(1L, updatedForum)).thenReturn(updatedForum);

        // Act & Assert
        mockMvc.perform(put("/api/forums/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedForum)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateForum_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        when(forumService.updateForum(99L, updatedForum)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/forums/update/99")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedForum)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteForum_WhenExists_ShouldReturn200() throws Exception {
        // Arrange
        when(forumService.deleteForum(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/forums/delete/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteForum_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        when(forumService.deleteForum(99L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/forums/delete/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
