
package com.yourcity.domain.domain.model.entity;

import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.domain.domain.model.enums.Role;
import com.yourcity.domain.domain.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String username;
    String email;
    String password;

    @Enumerated(STRING)
    Status status;

    @Column(name = "network_status")
    @Enumerated(STRING)
    NetworkStatus networkStatus;

    @Enumerated(STRING)
    Role role;

    @Column(name = "is_verified")
    Boolean isVerified;

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    List<Event> ownedEvents = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    List<Event> events = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    List<Chat> chats = new ArrayList<>();
}
