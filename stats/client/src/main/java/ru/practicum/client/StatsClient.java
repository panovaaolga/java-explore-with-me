package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
@RequiredArgsConstructor
@Slf4j
public class StatsClient {
    protected final HttpClient httpClient;
    private static final String GET_PREFIX = "/stats";
    private static final String POST_PREFIX = "/hit";
}
