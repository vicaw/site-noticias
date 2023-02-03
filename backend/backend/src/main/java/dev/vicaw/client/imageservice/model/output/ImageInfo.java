package dev.vicaw.client.imageservice.model.output;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {
    private UUID id;
    private String name;
    private Long article_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
