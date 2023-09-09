package ru.practicum.ewm.main.request.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.exception.EventNotFoundException;
import ru.practicum.ewm.main.event.repository.EventRepository;
import ru.practicum.ewm.main.request.entity.Request;
import ru.practicum.ewm.main.request.exception.RequestNotFoundException;
import ru.practicum.ewm.main.request.repository.RequestRepository;
import ru.practicum.ewm.main.request.service.RequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.main.request.entity.Request.Status.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Request create(Long requesterId, Long eventId) {
        if (requestRepository.findByRequesterIdAndEventId(requesterId, eventId).size() > 0) {
            throw new DataIntegrityViolationException("The request is repeated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (!event.getState().equals(Event.State.PUBLISHED)) {
            throw new DataIntegrityViolationException("The event is not published");
        }

        if (event.getInitiator().getId().equals(requesterId)) {
            throw new DataIntegrityViolationException("The initiator can not be requester");
        }

        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() < requestRepository.countByEventIdAndStatus(eventId, CONFIRMED) + 1) {
            throw new DataIntegrityViolationException("The limit of requests has been reached");
        }

        Request request = Request
                .builder()
                .requesterId(requesterId)
                .eventId(eventId)
                .status(event.getParticipantLimit() == 0 || !event.getRequestModeration() ? CONFIRMED : PENDING)
                .createDate(LocalDateTime.now())
                .build();

        return requestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Request findById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> findByRequesterId(Long requesterId) {
        return requestRepository.findByRequesterId(requesterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> findByUserIdAndEventId(Long userId, Long eventId) {
        return requestRepository.findByEventId(eventId);
    }

    @Override
    public Request cancel(Long requesterId, Long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, requesterId).orElseThrow(() -> new RequestNotFoundException(requestId));
        request.setStatus(CANCELED);

        return requestRepository.save(request);
    }

    @Override
    public List<Request> updateStatus(Long userId, Long eventId, List<Long> requestIds, Request.Status status) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new EventNotFoundException(eventId));
        List<Request> allRequests = requestRepository.findByEventId(eventId);
        long totalConfirmed = 0;
        Integer participantLimit = event.getParticipantLimit();
        if (status.equals(CONFIRMED)) {
            long confirmed = allRequests.stream().filter(r -> r.getStatus().equals(CONFIRMED)).count();
            totalConfirmed = confirmed + requestIds.size();
            if (participantLimit > 0 && participantLimit < totalConfirmed) {
                throw new DataIntegrityViolationException("Too many requests for confirmation");
            }
        }

        List<Request> resultList = new ArrayList<>();

        for (Request request : allRequests) {
            if (requestIds.contains(request.getId())) {
                if (!request.getStatus().equals(PENDING)) {
                    throw new DataIntegrityViolationException("Request reqId=" + request.getId() + " must have status PENDING");
                }
                request.setStatus(status);
                resultList.add(request);
            } else if (participantLimit > 0 && status.equals(CONFIRMED) && request.getStatus().equals(PENDING) && totalConfirmed == participantLimit) {
                request.setStatus(REJECTED);
                resultList.add(request);
            }
        }
        requestRepository.saveAll(allRequests);

        return resultList;
    }
}
