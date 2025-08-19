package AluraChallenge.ApiForoLeonardoCorales.dto.topic;


import java.time.OffsetDateTime;


public record TopicDetailDTO(
        Long id,
        String title,
        String message,
        OffsetDateTime createdAt,
        String status,
        String authorEmail,
        String authorName,
        String courseName
) {}