package internship.lmssystemofinternship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.lmssystemofinternship.Entity.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
    // JpaRepository provides findAll, save, and findById(Long id)
}