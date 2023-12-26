import java.util.List;

public interface StatsService {
    List<StatsDtoOutput> getStats(String start, String end, String[] uris, boolean unique, int from, int size);

    Stats addHit(StatsDtoInput statsDtoInput);
}
