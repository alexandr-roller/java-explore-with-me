package ru.practicum.ewm.main.request.entity.attributeconverter;

import ru.practicum.ewm.main.request.entity.Request;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static ru.practicum.ewm.main.request.entity.Request.Status.*;

@Converter(autoApply = true)
public class RequestStatusConverter implements AttributeConverter<Request.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Request.Status requestStatus) {
        switch (requestStatus) {
            case PENDING:
                return 1;
            case CONFIRMED:
                return 2;
            case REJECTED:
                return 3;
            case CANCELED:
                return 4;
            default:
                return null;
        }
    }

    @Override
    public Request.Status convertToEntityAttribute(Integer id) {
        switch (id) {
            case 1:
                return PENDING;
            case 2:
                return CONFIRMED;
            case 3:
                return REJECTED;
            case 4:
                return CANCELED;
            default:
                return null;
        }
    }
}
