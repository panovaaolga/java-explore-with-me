import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    @Query("select new ru.practicum.stats.dto.StatsDtoOutput(s.app, s.uri, count(s.id)) " +
            "from Stats as s " +
            "where cast(s.timestamp as timestamp) between " +
            "cast(?1 as timestamp) and cast(?2 as timestamp) " +
            "group by " + //группировка
            "order by count(s.id)")
    Page<StatsDtoOutput> findHits(String start, String end, boolean unique, Pageable pageable);

    @Query("select new ru.practicum.stats.dto.StatsDtoOutput(s.app, s.uri, count(s.id)) " +
            "from Stats as s " +
            "where s.uri in (?3) and " +
            "cast(s.timestamp as timestamp) between " +
            "cast(?1 as timestamp) and cast(?2 as timestamp) " +
            "group by " + //группировка
            "order by count(s.id)")
    Page<StatsDtoOutput> findHits(String start, String end, boolean unique, String[] uris, Pageable pageable);
}
