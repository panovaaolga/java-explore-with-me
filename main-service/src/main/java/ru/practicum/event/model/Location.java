package ru.practicum.event.model;

import lombok.Data;
import ru.practicum.event.dto.LocationId;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@IdClass(LocationId.class)
@Data
public class Location {
    @Id
    @Column(name = "lat")
    private double lat;
    @Id
    @Column(name = "lon")
    private double lon;
}
