package ru.practicum.ewm.main.subscription.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbstn_id")
    private Long id;
    @Column(name = "subscriber_id")
    private Long subsId;
    @Column(name = "user_user_id")
    private Long userId;
}
