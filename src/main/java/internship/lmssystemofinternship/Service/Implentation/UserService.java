package internship.lmssystemofinternship.Service.Implentation;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.user;
import internship.lmssystemofinternship.Repository.UserRepositoy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoy userRepositoy;
    private final ModelMapper modelMapper;
    private  UserDto converToDto(user users){
        return modelMapper.map(users,UserDto.class);
    }
    private  user converToEntity(UserDto users){
        return modelMapper.map(users,user.class);
    }
    public UserDto getUserByid(Long userId) {

        user users =  userRepositoy.findById(userId);
        return converToDto(users);
    }
    public List<UserDto> getAllUsers() {
        user users = (user) userRepositoy.findAll();
        return Collections.singletonList(converToDto(users));
    }
    public UserDto getUserByEmail(String email) {
        user users = userRepositoy.findByEmail(email);
        return converToDto(users);
    }
    public UserDto getByCOurseId(Long courseId) {
        user users = userRepositoy.findByCourseId(courseId);
        return converToDto(users);
    }
}
