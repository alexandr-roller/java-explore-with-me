package ru.practicum.ewm.stats.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.common.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.ewm.common.CommonFormats.DATE_TIME_FORMATTER;

@Service
@Slf4j
public class StatsClient {
    private final RestTemplate rest;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String statsUrl, RestTemplateBuilder builder) {
        rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(statsUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public ResponseEntity<Object> hit(EndpointHitDto hitDto) {
        return makeAndSendRequest(HttpMethod.POST, "/hit", null, hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String path = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
        Map<String, Object> parameters = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "uris", String.join(",", uris),
                "unique", unique
        );
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private ResponseEntity<Object> prepareResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(
            HttpMethod method,
            String path,
            @Nullable Map<String, Object> parameters,
            @Nullable T body
    ) {
        log.info("Request method={}, path={}, parameters={}", method.toString(), path, parameters);
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> response;
        try {
            if (parameters != null) {
                response = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                response = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareResponse(response);
    }
}
