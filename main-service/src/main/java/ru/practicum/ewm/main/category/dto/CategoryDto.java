package ru.practicum.ewm.main.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CategoryDto {
    @Setter
    private Long id;
    @NotBlank
    @Length(min = 1, max = 50)
    private String name;
}
