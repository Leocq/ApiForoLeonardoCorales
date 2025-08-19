package AluraChallenge.ApiForoLeonardoCorales.service;

import AluraChallenge.ApiForoLeonardoCorales.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
