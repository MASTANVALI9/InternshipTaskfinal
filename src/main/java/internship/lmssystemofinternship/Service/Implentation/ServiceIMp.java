package internship.lmssystemofinternship.Service.Implentation;

import internship.lmssystemofinternship.Entity.user;
import internship.lmssystemofinternship.Repository.UserRepositoy;
import internship.lmssystemofinternship.Service.ServiceForInter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import internship.lmssystemofinternship.Dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceIMp implements ServiceForInter {
    private final UserRepositoy userRepository;
    private final ModelMapper modelMapper;

    @Override
    user saveuser(user users) {
        return userRepository.save(users);
    }

    @Override
    user getById(Long id){
        return userRepository.findById(id);
    }
    private UserDto convertToDTO(user users) {
        return modelMapper.map(users, UserDto.class);
    }

    // Convert DTO -> Entity
    private user convertToEntity(UserDto userDTO) {
        return modelMapper.map(userDTO, user.class);
    }

    @Override
    public  List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
