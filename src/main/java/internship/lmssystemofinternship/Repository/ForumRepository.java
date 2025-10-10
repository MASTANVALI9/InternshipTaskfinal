package internship.lmssystemofinternship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import internship.lmssystemofinternship.Entity.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query("SELECT f FROM Forum f WHERE f.course.id = :courseId")
    Forum findByCourseId(@Param("courseId") Long courseId);
}
