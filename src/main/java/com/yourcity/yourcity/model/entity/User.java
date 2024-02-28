package com.yourcity.yourcity.model.entity;

import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.model.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String email;
    String password;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "network_status")
    @Enumerated(EnumType.STRING)
    NetworkStatus networkStatus;

    @Column(name = "is_verified")
    Boolean isVerified;

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Event ownedEvent;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(
                    name = "user_event",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "event_id")
            )
    List<Event> events = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    List<Chat> chats = new ArrayList<>();
}
