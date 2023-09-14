package ru.practicum.ewm.main.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.common.EwmCommonMethods;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.compilation.entity.Compilation;
import ru.practicum.ewm.main.compilation.exception.CompilationNotFoundException;
import ru.practicum.ewm.main.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main.compilation.service.CompilationService;
import ru.practicum.ewm.main.event.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Compilation create(Compilation compilation, List<Long> eventIds) {
        return compilationRepository.save(compilation.withEvents(eventRepository.findByIdIn(eventIds)));
    }

    @Override
    public Compilation update(Compilation compilation, List<Long> eventIds) {
        Compilation compilationForUpdate = findById(compilation.getId());
        if (eventIds != null) {
            compilationForUpdate.setEvents(eventRepository.findByIdIn(eventIds));
        }
        BeanUtils.copyProperties(compilation, compilationForUpdate, EwmCommonMethods.getNullPropertyNames(compilation));

        return compilationRepository.save(compilationForUpdate);
    }

    @Override
    public void deleteById(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compilation> findAllByPinned(Boolean pinned, Integer from, Integer size) {
        return compilationRepository.findByPinnedWithEvents(pinned, EwmPageRequest.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public Compilation findById(Long compId) {
        return compilationRepository.findByIdWithEvents(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
    }
}
