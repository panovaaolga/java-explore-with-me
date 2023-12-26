import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;


    @Transactional
    @Override
    public Stats addHit(StatsDtoInput statsDtoInput) {
        Stats stats = StatsMapper.mapToNewStats(statsDtoInput);
        return statsRepository.save(stats);
    }

    @Override
    public List<StatsDtoOutput> getStats(String start, String end, String[] uris, boolean unique, int from, int size) {
        if (uris.length == 0) {
            return statsRepository.findHits(start, end, unique, PageRequest.of(from/size, size, Sort.by("hits")))
                    .getContent();
        } else {
            return statsRepository.findHits(start, end, unique, uris,
                            PageRequest.of(from/size, size, Sort.by("hits"))).getContent();
        }
    }



}
