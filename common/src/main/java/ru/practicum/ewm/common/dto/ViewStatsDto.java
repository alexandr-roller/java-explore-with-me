package ru.practicum.ewm.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}