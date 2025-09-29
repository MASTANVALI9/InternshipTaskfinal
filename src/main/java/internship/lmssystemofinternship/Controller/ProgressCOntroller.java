package internship.lmssystemofinternship.Controller;

import internship.lmssystemofinternship.Dto.ProgressDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ProgressDto saveProgress(@RequestBody ProgressDto dto) {
        return progressService.saveProgress(dto);
    }

    @GetMapping("/{id}")
    public ProgressDto getProgressById(@PathVariable Long id) {
        return progressService.getProgressById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ProgressDto> getProgressByUser(@PathVariable Long userId) {
        return progressService.getProgressByUser(userId);
    }

    @GetMapping("/course/{courseId}")
    public List<ProgressDto> getProgressByCourse(@PathVariable Long courseId) {
        return progressService.getProgressByCourse(courseId);
    }
}
