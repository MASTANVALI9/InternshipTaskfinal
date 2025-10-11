package internship.lmssystemofinternship.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ForumPostDto {
    private Long id;
    private String title;
    private String content;
    private Long forumId;
    private UserDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likesCount;
    private boolean isPinned;
    private List<ForumReplyDto> replies;
    private int replyCount;

    public void setAuthorId(long l) {
    }
}
