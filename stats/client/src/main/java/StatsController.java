import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsClient statsClient;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@NotBlank @RequestParam String start,
                                           @NotBlank @RequestParam String end,
                                   @RequestParam String[] uris,
                                   @RequestParam(defaultValue = "false") boolean unique,
                                   @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                   @Positive @RequestParam(defaultValue = "10") int size) {
        return statsClient.getStats(start, end, uris, unique, from, size);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody @Validated StatsDtoInput statsDtoInput) {
        return statsClient.addHit(statsDtoInput);
    }
}
