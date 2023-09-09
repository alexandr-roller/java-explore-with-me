package ru.practicum.ewm.main.event.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.event.dto.*;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.user.mapper.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {
    @Mapping(source = "location.lat", target = "lat")
    @Mapping(source = "location.lon", target = "lon")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "paid", expression = "java(dto.getPaid() == null ? false : dto.getPaid())")
    @Mapping(target = "participantLimit", expression = "java(dto.getParticipantLimit() == null ? 0 : dto.getParticipantLimit())")
    @Mapping(target = "requestModeration", expression = "java(dto.getRequestModeration() == null ? true : dto.getRequestModeration())")
    Event fromNewDTO(EventNewDto dto);

    @Mapping(source = "location.lat", target = "lat")
    @Mapping(source = "location.lon", target = "lon")
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "stateAction", target = "state")
    Event fromUpdatePrivateDTO(EventUpdatePrivateDto dto);

    @Mapping(source = "location.lat", target = "lat")
    @Mapping(source = "location.lon", target = "lon")
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "stateAction", target = "state")
    Event fromUpdateAdminDTO(EventUpdateAdminDto dto);

    @Mapping(source = "lat", target = "location.lat")
    @Mapping(source = "lon", target = "location.lon")
    @Mapping(source = "createDate", target = "createdOn")
    @Mapping(source = "publishDate", target = "publishedOn")
    EventFullDto toFullDTO(Event event);

    EventShortDto toShortDTO(Event event);

    List<EventShortDto> toShortDTOs(List<Event> event);

    List<EventFullDto> toFullDTOs(List<Event> events);

    default Event.State fromStateActionPrivate(EventUpdatePrivateDto.StateAction action) {
        if (action == null) {
            return null;
        }
        switch (action) {
            case SEND_TO_REVIEW:
                return Event.State.PENDING;
            case CANCEL_REVIEW:
                return Event.State.CANCELED;
            default:
                return null;
        }
    }

    default Event.State fromStateActionAdmin(EventUpdateAdminDto.StateAction action) {
        if (action == null) {
            return null;
        }
        switch (action) {
            case PUBLISH_EVENT:
                return Event.State.PUBLISHED;
            case REJECT_EVENT:
                return Event.State.CANCELED;
            default:
                return null;
        }
    }
}
