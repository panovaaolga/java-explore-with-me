package ru.practicum.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatsDtoOutput {
    private String app;
    private String uri;
    private long hits;
}
