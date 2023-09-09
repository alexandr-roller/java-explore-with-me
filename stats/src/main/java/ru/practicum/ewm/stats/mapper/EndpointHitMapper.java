package ru.practicum.ewm.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.common.dto.EndpointHitDto;
import ru.practicum.ewm.stats.entities.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    EndpointHit fromDTO(EndpointHitDto dto);
}
