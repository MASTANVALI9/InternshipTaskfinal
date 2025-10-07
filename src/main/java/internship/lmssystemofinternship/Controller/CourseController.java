package internship.lmssystemofinternship.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import internship.lmssystemofinternship.Dto.CourseDto;
import internship.lmssystemofinternship.Service.Implentation.CourseSerivce;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseSerivce courseSerivce;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseSerivce.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(courseSerivce.getcourseByid(courseId));
    }

    @PostMapping("/add")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
        CourseDto created = courseSerivce.createCourse(courseDto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/update/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long courseId, @RequestBody CourseDto courseDto) {
        CourseDto updated = courseSerivce.updateCourse(courseId, courseDto);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/delete/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseSerivce.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enroll/{courseId}/student/{studentId}")
    public ResponseEntity<Void> enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseSerivce.enrollStudent(courseId, studentId);
        return ResponseEntity.ok().build();
    }


}
