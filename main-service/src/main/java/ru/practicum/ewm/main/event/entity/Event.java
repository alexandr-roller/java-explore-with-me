package ru.practicum.ewm.main.event.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "annotation")
    private String annotation;

    @With
    @ManyToOne
    @JoinColumn(name = "cat_cat_id")
    private Category category;

    @With
    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User initiator;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @With
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @With
    @Column(name = "evst_evst_id")
    private State state;

    @Transient
    private Long views;

    @Formula(value = "(select count(*) from requests r where r.event_event_id = event_id and r.rqst_rqst_id = 2)")
    @Basic(fetch = FetchType.EAGER)
    private Integer confirmedRequests;

    public enum State {
        PENDING, PUBLISHED, CANCELED
    }
}
