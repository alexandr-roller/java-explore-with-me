package ru.practicum.ewm.main.compilation.entity;

import lombok.*;
import ru.practicum.ewm.main.event.entity.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "pinned")
    private Boolean pinned;

    @With
    @ManyToMany
    @JoinTable(name = "events_compilations",
            joinColumns = @JoinColumn(name = "comp_comp_id", referencedColumnName = "comp_id"),
            inverseJoinColumns = @JoinColumn(name = "event_event_id", referencedColumnName = "event_id"))
    private List<Event> events;
}
