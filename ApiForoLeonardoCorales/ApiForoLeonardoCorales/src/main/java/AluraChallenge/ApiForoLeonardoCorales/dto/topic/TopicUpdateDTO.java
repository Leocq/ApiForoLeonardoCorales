package AluraChallenge.ApiForoLeonardoCorales.dto.topic;


import jakarta.validation.constraints.*;


public record TopicUpdateDTO(
        @NotBlank @Size(max = 150) String title,
        @NotBlank String message,
        @NotNull String status
) {}