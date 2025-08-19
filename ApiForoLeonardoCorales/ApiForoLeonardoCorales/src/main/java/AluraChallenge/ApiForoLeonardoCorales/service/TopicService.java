package AluraChallenge.ApiForoLeonardoCorales.service;

import AluraChallenge.ApiForoLeonardoCorales.domain.course.Course;
import AluraChallenge.ApiForoLeonardoCorales.domain.topic.Topic;
import AluraChallenge.ApiForoLeonardoCorales.domain.topic.TopicRepository;
import AluraChallenge.ApiForoLeonardoCorales.domain.topic.TopicStatus;
import AluraChallenge.ApiForoLeonardoCorales.domain.user.User;
import AluraChallenge.ApiForoLeonardoCorales.domain.user.UserRepository;
import AluraChallenge.ApiForoLeonardoCorales.dto.topic.TopicCreateDTO;
import AluraChallenge.ApiForoLeonardoCorales.dto.topic.TopicDetailDTO;
import AluraChallenge.ApiForoLeonardoCorales.dto.topic.TopicListDTO;
import AluraChallenge.ApiForoLeonardoCorales.dto.topic.TopicUpdateDTO;
import AluraChallenge.ApiForoLeonardoCorales.exception.IntegrityViolationException;
import AluraChallenge.ApiForoLeonardoCorales.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository; // asegúrate de tener este repo creado

    @Transactional
    public TopicDetailDTO create(TopicCreateDTO dto) {
        if (topicRepository.existsByTitleAndMessage(dto.title(), dto.message())) {
            throw new IntegrityViolationException("Tópico duplicado: mismo título y mensaje");
        }

        User author = userRepository.findById(dto.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Topic t = Topic.builder()
                .title(dto.title())
                .message(dto.message())
                .createdAt(OffsetDateTime.now())
                .status(TopicStatus.ABIERTO)
                .author(author)
                .course(course)
                .build();

        t = topicRepository.save(t);
        return mapDetail(t);
    }

    @Transactional(readOnly = true)
    public Page<TopicListDTO> list(Pageable pageable) {
        // Carga author y course para evitar LazyInitializationException
        return topicRepository.findPageWithAuthorCourse(pageable).map(this::mapList);
    }

    @Transactional(readOnly = true)
    public Page<TopicListDTO> listTop10Asc(Pageable pageableIgnored) {
        return topicRepository
                .findTop10AscWithAuthorCourse(PageRequest.of(0, 10, Sort.by("createdAt").ascending()))
                .map(this::mapList);
    }

    @Transactional(readOnly = true)
    public Page<TopicListDTO> searchByCourseAndYear(String courseName, int year, Pageable pageable) {
        return topicRepository.searchByCourseAndYearWithJoins(courseName, year, pageable).map(this::mapList);
    }

    @Transactional(readOnly = true)
    public TopicDetailDTO get(Long id) {
        Topic t = topicRepository.findByIdWithAuthorCourse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        return mapDetail(t);
    }

    @Transactional
    public TopicDetailDTO update(Long id, TopicUpdateDTO dto) {
        Topic t = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));

        boolean wouldDuplicate = topicRepository.existsByTitleAndMessage(dto.title(), dto.message());
        boolean sameCurrent = t.getTitle().equals(dto.title()) && t.getMessage().equals(dto.message());
        if (wouldDuplicate && !sameCurrent) {
            throw new IntegrityViolationException("Actualización duplicaría un tópico existente");
        }

        t.setTitle(dto.title());
        t.setMessage(dto.message());
        t.setStatus(TopicStatus.valueOf(dto.status().toUpperCase()));

        return mapDetail(t);
    }

    @Transactional
    public void delete(Long id) {
        Topic t = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        topicRepository.delete(t);
    }

    // ===================== Mappers =====================

    private TopicListDTO mapList(Topic t) {
        return new TopicListDTO(
                t.getId(),
                t.getTitle(),
                t.getCreatedAt(),
                t.getStatus().name(),
                t.getCourse().getName()
        );
    }

    private TopicDetailDTO mapDetail(Topic t) {
        return new TopicDetailDTO(
                t.getId(),
                t.getTitle(),
                t.getMessage(),
                t.getCreatedAt(),
                t.getStatus().name(),
                t.getAuthor().getEmail(),
                t.getAuthor().getDisplayName(),
                t.getCourse().getName()
        );
    }
}
