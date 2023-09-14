package ru.practicum.ewm.main.compilation.service;

import ru.practicum.ewm.main.compilation.entity.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation create(Compilation compilation, List<Long> eventIds);

    Compilation update(Compilation compilation, List<Long> eventIds);

    void deleteById(Long compId);

    List<Compilation> findAllByPinned(Boolean pinned, Integer from, Integer size);

    Compilation findById(Long compId);
}
