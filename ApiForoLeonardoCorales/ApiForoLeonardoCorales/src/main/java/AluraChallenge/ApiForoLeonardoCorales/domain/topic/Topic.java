package AluraChallenge.ApiForoLeonardoCorales.domain.topic;


import AluraChallenge.ApiForoLeonardoCorales.domain.course.Course;
import AluraChallenge.ApiForoLeonardoCorales.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;


@Entity @Table(name = "topics",
        uniqueConstraints = @UniqueConstraint(name = "uk_topic_title_msg", columnNames = {"title","message"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 150)
    private String title;


    @Column(nullable = false)
    private OffsetDateTime createdAt;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TopicStatus status;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

}