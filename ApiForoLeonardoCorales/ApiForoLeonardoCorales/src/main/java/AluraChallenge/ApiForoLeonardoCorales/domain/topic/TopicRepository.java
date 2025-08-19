package AluraChallenge.ApiForoLeonardoCorales.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleAndMessage(String title, String message);

    // ---------- Listado con author y course  ----------
    @EntityGraph(attributePaths = {"author", "course"})
    @Query("SELECT t FROM Topic t")
    Page<Topic> findPageWithAuthorCourse(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "course"})
    @Query("SELECT t FROM Topic t ORDER BY t.createdAt ASC")
    Page<Topic> findTop10AscWithAuthorCourse(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "course"})
    @Query("SELECT t FROM Topic t WHERE t.id = :id")
    Optional<Topic> findByIdWithAuthorCourse(@Param("id") Long id);

    //  Búsquedas con joins cargados
    @EntityGraph(attributePaths = {"author", "course"})
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName AND FUNCTION('YEAR', t.createdAt) = :year")
    Page<Topic> searchByCourseAndYearWithJoins(@Param("courseName") String courseName,
                                               @Param("year") int year,
                                               Pageable pageable);

    @EntityGraph(attributePaths = {"author", "course"})
    @Query("SELECT t FROM Topic t WHERE t.createdAt BETWEEN :from AND :to")
    Page<Topic> searchByDateRangeWithJoins(@Param("from") OffsetDateTime from,
                                           @Param("to") OffsetDateTime to,
                                           Pageable pageable);

    Page<Topic> findAllByOrderByCreatedAtAsc(Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName AND FUNCTION('YEAR', t.createdAt) = :year")
    Page<Topic> searchByCourseAndYear(@Param("courseName") String courseName,
                                      @Param("year") int year,
                                      Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.createdAt BETWEEN :from AND :to")
    Page<Topic> searchByDateRange(@Param("from") OffsetDateTime from,
                                  @Param("to") OffsetDateTime to,
                                  Pageable pageable);
}
