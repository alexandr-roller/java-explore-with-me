package ru.practicum.ewm.main.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Builder
public class CompilationUpdateDto {
    @Setter
    private Long id;
    @Length(min = 1, max = 50)
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
