package internship.lmssystemofinternship.Service.Implentation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import internship.lmssystemofinternship.Dto.ProgressDto;
import internship.lmssystemofinternship.Entity.Progress;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final ModelMapper modelMapper;
    private final ProgressRepository progressRepository;

    private ProgressDto convertToDTO(Progress progresss) {
        return modelMapper.map(progresss, ProgressDto.class);
    }

    private Progress convertToEntity(ProgressDto progressDTO) {
        return modelMapper.map(progressDTO, Progress.class);
    }

    public ProgressDto  saveProgress(ProgressDto dto) {
        Progress progress = convertToEntity(dto);
        Progress saved = progressRepository.save(progress);
        return convertToDTO(saved);
    }


    public ProgressDto getProgressById(Long id) {
        return progressRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    public List<ProgressDto> getprogressByUser(Long userId) {
        return progressRepository.findByUser_Id(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProgressDto> getprogressByCourse(Long courseId) {
        return progressRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}