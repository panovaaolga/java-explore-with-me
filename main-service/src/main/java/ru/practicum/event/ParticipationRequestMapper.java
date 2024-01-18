package ru.practicum.event;

import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.ParticipationRequest;

import java.util.ArrayList;
import java.util.List;

public class ParticipationRequestMapper {

    public static List<ParticipationRequestDto> mapToDtoList(List<ParticipationRequest> requests) {
        List<ParticipationRequestDto> requestDtos = new ArrayList<>();
        if (!requests.isEmpty()) {
            for (ParticipationRequest p : requests) {
                requestDtos.add(mapToDto(p));
            }
        }
        return requestDtos;
    }

    public static ParticipationRequestDto mapToDto(ParticipationRequest request) {
        ParticipationRequestDto requestDto = new ParticipationRequestDto();
        requestDto.setId(request.getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setCreated(request.getCreated());
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }
}
