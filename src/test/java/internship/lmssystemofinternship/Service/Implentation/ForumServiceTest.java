package internship.lmssystemofinternship.Service.Implentation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import internship.lmssystemofinternship.Entity.Forum;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.ForumRepository;

@ExtendWith(MockitoExtension.class)
class ForumServiceTest {

    @Mock
    private ForumRepository forumRepository;

    @InjectMocks
    private ForumService forumService;

    private Forum testForum;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRoles(Set.of(Roles.STUDENT));

        testForum = new Forum();
        testForum.setId(1L);
        testForum.setTitle("Test Forum");
        testForum.setDescription("Test Forum Description");
        testForum.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getAllForums_ShouldReturnListOfForums() {
        // Arrange
        List<Forum> forums = Arrays.asList(testForum);
        when(forumRepository.findAll()).thenReturn(forums);

        // Act
        List<Forum> result = forumService.getAllForums();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Forum", result.get(0).getTitle());
        verify(forumRepository).findAll();
    }

    @Test
    void getForumById_WhenForumExists_ShouldReturnForum() {
        // Arrange
        when(forumRepository.findById(1L)).thenReturn(Optional.of(testForum));

        // Act
        Forum result = forumService.getForumById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Forum", result.getTitle());
        verify(forumRepository).findById(1L);
    }

    @Test
    void getForumById_WhenForumDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(forumRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Forum result = forumService.getForumById(1L);

        // Assert
        assertNull(result);
        verify(forumRepository).findById(1L);
    }

    @Test
    void createForum_ShouldSaveAndReturnForum() {
        // Arrange
        when(forumRepository.save(testForum)).thenReturn(testForum);

        // Act
        Forum result = forumService.createForum(testForum);

        // Assert
        assertNotNull(result);
        assertEquals("Test Forum", result.getTitle());
        verify(forumRepository).save(testForum);
    }

    @Test
    void updateForum_WhenForumExists_ShouldUpdateAndReturnForum() {
        // Arrange
        Forum updatedForum = new Forum();
        updatedForum.setTitle("Updated Forum");
        updatedForum.setDescription("Updated Description");

        when(forumRepository.existsById(1L)).thenReturn(true);
        when(forumRepository.save(updatedForum)).thenReturn(updatedForum);

        // Act
        Forum result = forumService.updateForum(1L, updatedForum);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Forum", result.getTitle());
        verify(forumRepository).existsById(1L);
        verify(forumRepository).save(updatedForum);
    }

    @Test
    void updateForum_WhenForumDoesNotExist_ShouldReturnNull() {
        // Arrange
        Forum updatedForum = new Forum();
        when(forumRepository.existsById(1L)).thenReturn(false);

        // Act
        Forum result = forumService.updateForum(1L, updatedForum);

        // Assert
        assertNull(result);
        verify(forumRepository).existsById(1L);
        verify(forumRepository, never()).save(any());
    }

    @Test
    void deleteForum_WhenForumExists_ShouldReturnTrue() {
        // Arrange
        when(forumRepository.existsById(1L)).thenReturn(true);
        doNothing().when(forumRepository).deleteById(1L);

        // Act
        boolean result = forumService.deleteForum(1L);

        // Assert
        assertTrue(result);
        verify(forumRepository).existsById(1L);
        verify(forumRepository).deleteById(1L);
    }

    @Test
    void deleteForum_WhenForumDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(forumRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = forumService.deleteForum(1L);

        // Assert
        assertFalse(result);
        verify(forumRepository).existsById(1L);
        verify(forumRepository, never()).deleteById(any());
    }
}
