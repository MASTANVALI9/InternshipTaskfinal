package internship.lmssystemofinternship.Service.Implentation;

import internship.lmssystemofinternship.Dto.ProgressDto;
import internship.lmssystemofinternship.Entity.progress;
import internship.lmssystemofinternship.Repository.ProgressRepository;
import internship.lmssystemofinternship.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgreessSerrvice {
    private final ModelMapper modelMapper;
    private final ProgressRepository progressRepository;

    private ProgressDto convertToDTO(progress progresss) {
        return modelMapper.map(progresss, ProgressDto.class);
    }

    private progress convertToEntity(ProgressDto progressDTO) {
        return modelMapper.map(progressDTO, progress.class);
    }

    public ProgressDto  saveProgress(ProgressDto dto) {
        progress progresss = convertToEntity(dto);
        progress saved = progressRepository.save(progresss);
        return convertToDTO(saved);
    }

    public ProgressDto getProgressById(Long id) {
        return progressRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<ProgressDto> getprogressByUser(Long userId) {
        return progressRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProgressDto> getProgressByCourse(Long courseId) {
        return progressRepository.findByCourseId(courseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}