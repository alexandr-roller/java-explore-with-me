package ru.practicum.ewm.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(value = "/compilations")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CompilationPublicController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping
    public List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get compilations");
        return compilationMapper.toDTOs(compilationService.findAllByPinned(pinned, from, size));
    }

    @GetMapping(value = "/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Get compilation compId={}", compId);
        return compilationMapper.toDTO(compilationService.findById(compId));
    }
}
