package ru.practicum.ewm.main.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class UserShortDto {
    private Long id;
    @NotBlank
    private String name;
}
