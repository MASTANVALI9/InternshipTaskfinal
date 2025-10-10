package internship.lmssystemofinternship.Dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumReplyDto {
    private Long id;
    private String content;
    private Long postId;
    private UserDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likesCount;
    private int replyLevel; // 0 for direct reply, 1 for replies to replies, etc.
}
