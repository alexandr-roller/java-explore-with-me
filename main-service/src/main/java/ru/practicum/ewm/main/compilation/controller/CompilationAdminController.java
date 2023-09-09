package ru.practicum.ewm.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.main.compilation.dto.CompilationUpdateDto;
import ru.practicum.ewm.main.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin/compilations")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CompilationAdminController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Valid CompilationNewDto dto) {
        log.info("Post compilation");
        return compilationMapper.toDTO(
                compilationService.create(compilationMapper.fromNewDTO(dto), ListUtils.emptyIfNull(dto.getEvents()))
        );
    }

    @DeleteMapping(value = "/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        log.info("Delete compilation compId={}", compId);
        compilationService.deleteById(compId);
    }

    @PatchMapping(value = "/{compId}")
    public CompilationDto update(
            @PathVariable Long compId,
            @RequestBody @Valid CompilationUpdateDto dto
    ) {
        log.info("Patch compilation compId={}", compId);
        dto.setId(compId);
        return compilationMapper.toDTO(
                compilationService.update(compilationMapper.fromUpdateDTO(dto), dto.getEvents())
        );
    }
}
