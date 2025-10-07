package internship.lmssystemofinternship.Controller;

import internship.lmssystemofinternship.Dto.ProgressDto;
import internship.lmssystemofinternship.Service.Implentation.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;
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

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProgressDto>> GetProgressByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(progressService.getprogressByCourse(courseId));
    }
//        return progressService.getProgressByCourse(courseId);
//    }

}
