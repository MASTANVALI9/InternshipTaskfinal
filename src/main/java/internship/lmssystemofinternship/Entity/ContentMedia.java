package internship.lmssystemofinternship.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "content_media")
@Getter
@Setter
public class ContentMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ContentType contentType; // VIDEO, PDF, TEXT, EXTERNAL_LINK
    
    private String fileUrl; // URL or path to the content
    private String fileName;
    private Long fileSize; // in bytes
    private Integer durationInMinutes; // for videos
    
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    
    private LocalDateTime uploadedAt;
    private boolean isProcessed; // for video processing status
    
    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
    
    public enum ContentType {
        VIDEO, PDF, TEXT, EXTERNAL_LINK, IMAGE, AUDIO
    }
}
