package ru.practicum.controller;

import ru.practicum.dto.StatsDtoInput;
import ru.practicum.dto.StatsDtoOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.StatsService;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public List<StatsDtoOutput> getStats(@RequestParam @PastOrPresent
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                         @RequestParam
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                         @RequestParam(required = false) String[] uris,
                                         @RequestParam(defaultValue = "false") boolean unique,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique, from, size);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody @Validated StatsDtoInput statsDtoInput) {
        log.info("statsDtoInput = {}", statsDtoInput);
        statsService.addHit(statsDtoInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
