package ru.practicum.event.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "locations")
@IdClass(Location.class)
public class Location implements Serializable {
    @Id
    @Column
    private String lat;
    @Id
    @Column
    private String lon;
}
