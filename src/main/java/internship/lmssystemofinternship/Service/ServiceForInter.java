package internship.lmssystemofinternship.Service;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.User;

import java.util.List;

public interface ServiceForInter {
    User saveuser (User users);
    User getById(Long id);
    public  List<UserDto> getAllUsers();
}
