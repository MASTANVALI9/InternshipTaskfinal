package internship.lmssystemofinternship.Service.Implentation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import internship.lmssystemofinternship.Dto.UserDto;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    public UserDto createUser(UserDto userDto) {
        User user = converToEntity(userDto);
        User saved = userRepositoy.save(user);
        return converToDto(saved);
    }
    private final UserRepository userRepositoy;
    private final ModelMapper modelMapper;
    private UserDto converToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private UserDto converToDto(Optional<User> users) {
        return users.map(u -> modelMapper.map(u, UserDto.class)).orElse(null);
    }

    private  User converToEntity(UserDto users){
        return modelMapper.map(users,User.class);
    }
    public UserDto getUserByid(Long userId) {
        Optional<User> users =  userRepositoy.findById(userId);
        return converToDto(users);
    }
    public List<UserDto> getAllUsers() {
        return userRepositoy.findAll()
                .stream()
                .map(this::converToDto)
                .toList();
    }
    public UserDto getUserByEmail(String email) {
        Optional<User> users = userRepositoy.findByEmail(email);
        return converToDto(users);
    }
//    public List<UserDto> getByCourseId(Long courseId) {
//        List<user> users = userRepositoy.findByCourses_CourseId(courseId);
//        return users.stream()
//                .map(this::converToDto)
//                .collect(Collectors.toList());


}
