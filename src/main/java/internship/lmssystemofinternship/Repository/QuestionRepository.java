package internship.lmssystemofinternship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.lmssystemofinternship.Entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // JpaRepository provides findAll, findById, save, deleteById
}