package internship.lmssystemofinternship.Controller;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Dto.ProgressDto;
import internship.lmssystemofinternship.Service.Implentation.CourseSerivce;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseSerivce courseSerivce;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseSerivce.getAllCourses());
    }
    @GetMapping("/Id")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseSerivce.getcourseByid(courseId));
    }


}
