package ru.practicum.ewm.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.mapper.RequestMapper;
import ru.practicum.ewm.main.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @GetMapping
    public List<RequestDto> getByUserId(@PathVariable Long userId) {
        log.info("Get requests userId={}", userId);
        return requestMapper.toDTOs(requestService.findByRequesterId(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("Post request userId={}, eventId={}", userId, eventId);
        return requestMapper.toDTO(requestService.create(userId, eventId));
    }

    @PatchMapping(value = "/{requestId}/cancel")
    public RequestDto cancel(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("Cancel request userId={}, requestId={}", userId, requestId);
        return requestMapper.toDTO(requestService.cancel(userId, requestId));
    }
}
