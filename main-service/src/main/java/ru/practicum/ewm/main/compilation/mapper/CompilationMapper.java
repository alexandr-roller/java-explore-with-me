package ru.practicum.ewm.main.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.main.compilation.dto.CompilationUpdateDto;
import ru.practicum.ewm.main.compilation.entity.Compilation;
import ru.practicum.ewm.main.event.mapper.EventMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface CompilationMapper {
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "pinned", expression = "java(dto.getPinned() == null ? false : dto.getPinned())")
    Compilation fromNewDTO(CompilationNewDto dto);

    @Mapping(target = "events", ignore = true)
    Compilation fromUpdateDTO(CompilationUpdateDto dto);

    CompilationDto toDTO(Compilation entity);

    List<CompilationDto> toDTOs(List<Compilation> entities);
}
