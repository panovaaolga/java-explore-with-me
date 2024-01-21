package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class StatsClient {
    private static final String GET_PREFIX = "/stats";
    private static final String POST_PREFIX = "/hit";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ewm-stats-server.url}")
    private String statsUrl;

    public List<ViewStats> getStats(String start, String end, String[] uris, boolean unique, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique,
                "from", from,
                "size", size
        );

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(statsUrl + GET_PREFIX)
                .queryParam("start", "{start}")
                .queryParam("end", "{end}")
                .queryParam("uris", "{uris}")
                .queryParam("unique", "{unique}")
                .queryParam("from", "{from}")
                .queryParam("size", "{size}")
                .encode()
                .toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ViewStats[]> response = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                entity, ViewStats[].class, parameters);
        List<ViewStats> viewStats = List.of(response.getBody());
        log.info("List: {}", viewStats);
        return viewStats;
    }

    public EndpointHit addHit(EndpointHitDto endpointHitDto) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(statsUrl + POST_PREFIX).toUriString();
        ResponseEntity<EndpointHit> response = restTemplate.postForEntity(urlTemplate, endpointHitDto,
                EndpointHit.class);
        EndpointHit endpointHit = response.getBody();
        log.info("Endpoint Hit: {}", endpointHit);
        return endpointHit;
    }

}
