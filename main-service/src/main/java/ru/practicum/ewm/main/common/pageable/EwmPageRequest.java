package ru.practicum.ewm.main.common.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public class EwmPageRequest extends PageRequest {
    protected EwmPageRequest(int from, int size, Sort sort) {
        super(from / size, size, sort);
    }

    public static EwmPageRequest of(int from, int size, @NonNull Sort sort) {
        return new EwmPageRequest(from, size, sort);
    }

    public static EwmPageRequest of(int from, int size) {
        return of(from, size, Sort.unsorted());
    }
}
