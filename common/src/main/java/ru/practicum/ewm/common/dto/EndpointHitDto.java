package ru.practicum.ewm.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.common.CommonFormats;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonFormats.DATE_TIME_FORMAT)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
