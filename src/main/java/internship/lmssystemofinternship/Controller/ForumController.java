package internship.lmssystemofinternship.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.lmssystemofinternship.Entity.Forum;
import internship.lmssystemofinternship.Service.Implentation.ForumService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/all")
    public ResponseEntity<java.util.List<Forum>> getAllForums() {
        return ResponseEntity.ok(forumService.getAllForums());
    }

    @GetMapping("/{forumId}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long forumId) {
        Forum forum = forumService.getForumById(forumId);
        return forum != null ? ResponseEntity.ok(forum) : ResponseEntity.notFound().build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Forum> getForumByCourse(@PathVariable Long courseId) {
        Forum forum = forumService.getForumByCourse(courseId);
        return forum != null ? ResponseEntity.ok(forum) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
        return ResponseEntity.ok(forumService.createForum(forum));
    }

    @PutMapping("/update/{forumId}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long forumId, @RequestBody Forum forum) {
        Forum updated = forumService.updateForum(forumId, forum);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{forumId}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long forumId) {
        boolean deleted = forumService.deleteForum(forumId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
