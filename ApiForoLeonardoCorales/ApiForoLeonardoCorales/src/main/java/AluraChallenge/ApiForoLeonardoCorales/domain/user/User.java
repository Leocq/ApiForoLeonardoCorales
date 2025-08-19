package AluraChallenge.ApiForoLeonardoCorales.domain.user;


import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 120)
    private String email;


    @Column(nullable = false)
    private String passwordHash;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;


    @Column(nullable = false, length = 80)
    private String displayName;
}