package ru.practicum.ewm.main.request.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id")
    private Long id;
    @Column(name = "user_user_id")
    private Long requesterId;
    @Column(name = "event_event_id")
    private Long eventId;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "rqst_rqst_id")
    private Status status;

    public enum Status {
        PENDING, CONFIRMED, REJECTED, CANCELED
    }
}
