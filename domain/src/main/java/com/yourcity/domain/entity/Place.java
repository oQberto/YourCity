package com.yourcity.domain.entity;

import com.yourcity.domain.enums.PlaceCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.EnumType.STRING;
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
public class Place {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    String description;

    @Enumerated(STRING)
    PlaceCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    Address address;
}
