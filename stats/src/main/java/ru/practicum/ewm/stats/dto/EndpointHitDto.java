package ru.practicum.ewm.stats.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class EndpointHitDto {
    private Long id;
    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private String ip;
    @With
    private LocalDateTime timestamp;
}
