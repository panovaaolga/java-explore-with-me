package ru.practicum.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private long hits;
}
