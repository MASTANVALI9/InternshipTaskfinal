package internship.lmssystemofinternship.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ForumDto {
    private Long id;
    private String title;
    private String description;
    private Long courseId;
    private Long createdBy;
    private LocalDateTime createdAt;
    private boolean isActive;
    private List<ForumPostDto> posts;
    
    // Statistics
    private int totalPosts;
    private int totalReplies;
    private String lastActivity;
}
