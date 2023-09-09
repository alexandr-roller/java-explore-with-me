package ru.practicum.ewm.main.user.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;
import ru.practicum.ewm.main.user.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapFromDTO() {
        UserDto dto = UserDto.builder().id(1L).name("test").email("test@test.com").build();
        User entity = userMapper.fromDTO(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
    }

    @Test
    void shouldMapToDTO() {
        User entity = User.builder().id(1L).name("test").email("test@test.com").build();
        UserDto dto = userMapper.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getEmail(), dto.getEmail());
    }

    @Test
    void shouldMapToShortDTO() {
        User entity = User.builder().id(1L).name("test").email("test@test.com").build();
        UserShortDto dto = userMapper.toShortDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}