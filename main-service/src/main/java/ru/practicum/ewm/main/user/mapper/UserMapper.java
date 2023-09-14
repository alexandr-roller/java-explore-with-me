package ru.practicum.ewm.main.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromDTO(UserDto dto);

    UserDto toDTO(User entity);

    UserShortDto toShortDTO(User entity);

    List<UserDto> toDTOs(List<User> entities);
}
