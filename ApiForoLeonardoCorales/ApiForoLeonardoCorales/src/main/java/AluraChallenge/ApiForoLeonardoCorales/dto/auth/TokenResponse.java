package AluraChallenge.ApiForoLeonardoCorales.dto.auth;


public record TokenResponse(String tokenType, String accessToken, long expiresInMs) {}