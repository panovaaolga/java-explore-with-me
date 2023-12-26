import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    @GetMapping("/stats")
    public List<StatsDtoOutput> getStats(@RequestParam String start) {
        return null;
    }

    @PostMapping("/hit")
    public void addQuery() {

    }
}
