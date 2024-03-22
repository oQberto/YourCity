
package com.yourcity.domain.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = PRIVATE)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    String description;

    @Column(name = "event_time")
    LocalDateTime eventTime;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "owner_id")
    User owner;

    @Builder.Default
    @ManyToMany(mappedBy = "events")
    List<User> subscribedUsers = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    Address address;
}
