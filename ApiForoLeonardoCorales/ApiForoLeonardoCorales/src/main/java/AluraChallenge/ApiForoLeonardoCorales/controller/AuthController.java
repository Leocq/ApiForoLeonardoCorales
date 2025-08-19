package AluraChallenge.ApiForoLeonardoCorales.controller;


import AluraChallenge.ApiForoLeonardoCorales.dto.auth.*;
import AluraChallenge.ApiForoLeonardoCorales.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.authenticate(request.email(), request.password());
        return ResponseEntity.ok(new TokenResponse("Bearer", token, 3600000));
    }
}