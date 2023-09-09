package ru.practicum.ewm.main.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
public class CompilationNewDto {
    private Long id;
    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
