package ru.practicum.compilation;

import ru.practicum.category.Category;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.event.EventMapper;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompilationMapper {
    public static Compilation mapToCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.isPinned());
        compilation.setTitle(newCompilationDto.getTitle());
       // compilation.setEvents(events);
        return compilation;
    }

    public static CompilationDto mapToDto(Compilation compilation) {
        Set<EventShortDto> eventsShort = new HashSet<>();
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilationDto.getTitle());
        for (Event e : compilation.getEvents()) {
            eventsShort.add(EventMapper.mapToShortEvent(e));
        }
        compilationDto.setEvents(eventsShort);
        return compilationDto;
    }

    public static List<CompilationDto> mapToDtoList(List<Compilation> compilations) {
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation c : compilations) {
            compilationDtos.add(mapToDto(c));
        }
        return compilationDtos;
    }
}
