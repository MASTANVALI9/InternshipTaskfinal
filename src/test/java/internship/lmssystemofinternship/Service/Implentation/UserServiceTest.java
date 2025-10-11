package internship.lmssystemofinternship.Service.Implentation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Enum.Roles;
import internship.lmssystemofinternship.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of(Roles.STUDENT));

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setUsername("testuser");
        testUserDto.setPassword("password");
        testUserDto.setRole("STUDENT");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserDtos() {
        // Arrange
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(testUserDto);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void getUserByid_WhenUserExists_ShouldReturnUserDto() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(testUserDto);

        // Act
        UserDto result = userService.getUserByid(1L);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserByid_WhenUserDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        UserDto result = userService.getUserByid(1L);

        // Assert
        assertNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void createUser_ShouldSaveAndReturnUserDto() {
        // Arrange
        when(modelMapper.map(testUserDto, User.class)).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(testUserDto);

        // Act
        UserDto result = userService.createUser(testUserDto);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(testUser);
    }

    @Test
    void getUserByEmail_WhenUserExists_ShouldReturnUserDto() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(testUserDto);

        // Act
        UserDto result = userService.getUserByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void getUserByEmail_WhenUserDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        UserDto result = userService.getUserByEmail("nonexistent@example.com");

        // Assert
        assertNull(result);
        verify(userRepository).findByEmail("nonexistent@example.com");
    }
}
