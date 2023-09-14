package ru.practicum.ewm.main.stats.service;

import ru.practicum.ewm.main.event.entity.Event;

import java.util.List;

public interface StatsService {
    void loadViews(List<Event> events);

    void loadViews(Event event);
}
