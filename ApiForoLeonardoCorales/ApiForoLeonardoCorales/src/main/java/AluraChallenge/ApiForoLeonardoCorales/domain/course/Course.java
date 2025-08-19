package AluraChallenge.ApiForoLeonardoCorales.domain.course;


import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name = "courses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 120)
    private String name;
}