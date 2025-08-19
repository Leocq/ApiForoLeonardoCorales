package AluraChallenge.ApiForoLeonardoCorales.dto.topic;


import java.time.OffsetDateTime;


public record TopicListDTO(
        Long id,
        String title,
        OffsetDateTime createdAt,
        String status,
        String courseName
) {}