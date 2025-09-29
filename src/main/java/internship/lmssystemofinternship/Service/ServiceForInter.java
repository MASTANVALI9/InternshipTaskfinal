package internship.lmssystemofinternship.Service;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.user;

import java.util.List;

public interface ServiceForInter {
    user saveuser (user users);
    user getById(Long id);
    public  List<UserDto> getAllUsers()
}
