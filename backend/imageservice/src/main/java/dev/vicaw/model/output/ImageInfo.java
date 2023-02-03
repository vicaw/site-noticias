package dev.vicaw.model.output;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ImageInfo {
    private UUID id;
    private String name;
    private Long article_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
