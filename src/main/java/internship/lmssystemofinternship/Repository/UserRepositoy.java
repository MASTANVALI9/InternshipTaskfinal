package internship.lmssystemofinternship.Repository;

import internship.lmssystemofinternship.Entity.user;
import org.springframework.data.repository.Repository;

import java.lang.ScopedValue;
import java.util.List;

public interface UserRepositoy extends Repository<user, Long> {
    user findById(Long id);

    List<user> findAll();

    user save(user users);
}