package AluraChallenge.ApiForoLeonardoCorales.controller;


import AluraChallenge.ApiForoLeonardoCorales.dto.topic.*;
import AluraChallenge.ApiForoLeonardoCorales.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;


    @PostMapping
    public ResponseEntity<TopicDetailDTO> create(@RequestBody @Valid TopicCreateDTO dto,
                                                 UriComponentsBuilder uriBuilder) {
        TopicDetailDTO created = topicService.create(dto);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created); // HTTP 201
    }


    @GetMapping
    public Page<TopicListDTO> list(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam(required = false) String course,
                                   @RequestParam(required = false) Integer year,
                                   @RequestParam(required = false, defaultValue = "false") boolean ascTop10) {
        if (ascTop10) return topicService.listTop10Asc(PageRequest.of(0, 10, Sort.by("createdAt").ascending()));
        if (course != null && year != null) return topicService.searchByCourseAndYear(course, year, pageable);
        return topicService.list(pageable);
    }


    @GetMapping("/{id}")
    public TopicDetailDTO get(@PathVariable Long id) { return topicService.get(id); }


    @PutMapping("/{id}")
    public TopicDetailDTO update(@PathVariable Long id, @RequestBody @Valid TopicUpdateDTO dto) {
        return topicService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}