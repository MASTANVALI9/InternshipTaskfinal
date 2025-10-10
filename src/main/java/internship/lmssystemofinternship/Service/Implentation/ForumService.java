package internship.lmssystemofinternship.Service.Implentation;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.lmssystemofinternship.Entity.Forum;
import internship.lmssystemofinternship.Repository.ForumRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Forum getForumById(Long forumId) {
        return forumRepository.findById(forumId).orElse(null);
    }

    public Forum getForumByCourse(Long courseId) {
        return forumRepository.findByCourseId(courseId);
    }

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public Forum updateForum(Long forumId, Forum forum) {
        if (forumRepository.existsById(forumId)) {
            forum.setId(forumId);
            return forumRepository.save(forum);
        }
        return null;
    }

    public boolean deleteForum(Long forumId) {
        if (forumRepository.existsById(forumId)) {
            forumRepository.deleteById(forumId);
            return true;
        }
        return false;
    }
}
