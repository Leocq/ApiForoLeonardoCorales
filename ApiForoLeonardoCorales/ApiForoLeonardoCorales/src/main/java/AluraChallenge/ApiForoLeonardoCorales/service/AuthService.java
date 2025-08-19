package AluraChallenge.ApiForoLeonardoCorales.service;


import AluraChallenge.ApiForoLeonardoCorales.domain.user.User;
import AluraChallenge.ApiForoLeonardoCorales.domain.user.UserRepository;
import AluraChallenge.ApiForoLeonardoCorales.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;


    public String authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!encoder.matches(rawPassword, user.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return tokenService.generateToken(user);
    }
}