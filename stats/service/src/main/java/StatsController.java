import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public List<StatsDtoOutput> getStats(@RequestParam String start,
                                         @RequestParam String end,
                                         @RequestParam String[] uris,
                                         @RequestParam boolean unique,
                                         @RequestParam int from,
                                         @RequestParam int size) {
        return statsService.getStats(start, end, uris, unique, from, size);
    }

    @PostMapping("/hit")
    public Stats addHit(@RequestBody StatsDtoInput statsDtoInput) {
        return statsService.addHit(statsDtoInput);
    }
}
