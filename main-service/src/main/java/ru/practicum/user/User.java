package ru.practicum.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(name = "username")
    private String name;
    @ManyToMany
    @JoinTable(name = "subscribers_subscriptions", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany(mappedBy = "subscribers")
    private Set<User> subscriptions = new HashSet<>();
}
