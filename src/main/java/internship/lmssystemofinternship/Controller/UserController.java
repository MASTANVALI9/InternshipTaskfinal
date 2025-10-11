package internship.lmssystemofinternship.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Repository.UserRepository;
import internship.lmssystemofinternship.Service.Implentation.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserByid(userId);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addTeacher(@RequestBody UserDto userDto) {
        UserDto created = userService.createUser(userDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam("email") String email) {
        UserDto userDto = userService.getUserByEmail(email);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

//    @GetMapping("/course/{courseId}")
//    public ResponseEntity<List<UserDto>> getUserByCourseId(@PathVariable("courseId") Long courseId) {
//        UserDto userDto = (UserDto) userService.getByCourseId(courseId);
//        if (userDto == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(Collections.singletonList(userDto));
//    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findFirstByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        UserDto userDto = userService.converToDto(userOpt);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/profile/current")
    public ResponseEntity<?> getCurrentUserProfile(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            String username = authentication.getName();
            Optional<User> userOpt = userRepository.findFirstByUsername(username);

            if (userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOpt.get();
            // Return simplified user data for frontend
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("username", user.getUsername());
            userProfile.put("email", user.getEmail() != null ? user.getEmail() : "");
            userProfile.put("role", user.getRoles().iterator().next().name());

            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving user profile: " + e.getMessage());
        }
    }
}
