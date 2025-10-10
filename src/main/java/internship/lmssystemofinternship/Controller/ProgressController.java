package internship.lmssystemofinternship.Controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import internship.lmssystemofinternship.Dto.ProgressDto;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Repository.UserRepository;
import internship.lmssystemofinternship.Service.Implentation.ProgressService;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public  ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<ProgressDto> SaveProgress(@RequestBody ProgressDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(progressService.saveProgress(dto));
    }

    @GetMapping("/{id}")
    public ProgressDto GetProgressById(@PathVariable Long id) {

        return progressService.getProgressById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressDto>> GetProgressByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getprogressByUser(userId));
    }

    @GetMapping("/current")
    public ResponseEntity<List<ProgressDto>> getCurrentUserProgress(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String username = authentication.getName();
            Optional<User> userOpt = userRepository.findByUsername(username);

            if (userOpt.isEmpty()) {
                return ResponseEntity.ok(List.of()); // Return empty list instead of 404
            }

            Long userId = userOpt.get().getId();
            return ResponseEntity.ok(progressService.getprogressByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(List.of()); // Return empty list on error
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProgressDto>> GetProgressByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(progressService.getprogressByCourse(courseId));
    }
//        return progressService.getProgressByCourse(courseId);
//    }

}
