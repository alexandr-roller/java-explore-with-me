package ru.practicum.ewm.main.event.entity.attributeconverter;

import ru.practicum.ewm.main.event.entity.Event;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static ru.practicum.ewm.main.event.entity.Event.State.*;

@Converter(autoApply = true)
public class EventStateConverter implements AttributeConverter<Event.State, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Event.State eventState) {
        if (eventState == null) {
            return null;
        }
        switch (eventState) {
            case PENDING:
                return 1;
            case PUBLISHED:
                return 2;
            case CANCELED:
                return 3;
            default:
                return null;
        }
    }

    @Override
    public Event.State convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        switch (id) {
            case 1:
                return PENDING;
            case 2:
                return PUBLISHED;
            case 3:
                return CANCELED;
            default:
                return null;
        }
    }
}
