package internship.lmssystemofinternship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.lmssystemofinternship.Entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // JpaRepository provides findAll, findById, save
}