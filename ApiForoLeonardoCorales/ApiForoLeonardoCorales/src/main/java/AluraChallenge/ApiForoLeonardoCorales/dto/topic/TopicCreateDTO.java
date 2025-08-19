package AluraChallenge.ApiForoLeonardoCorales.dto.topic;


import jakarta.validation.constraints.*;


public record TopicCreateDTO(
        @NotBlank @Size(max = 150) String title,
        @NotBlank String message,
        @NotNull Long authorId,
        @NotNull Long courseId
) {}