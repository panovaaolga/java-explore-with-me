package ru.practicum.compilation;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompilationMapper {
    public static Compilation mapToCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.isPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setEvents(new HashSet<>(events));
        return compilation;
    }

    public static CompilationDto mapToDto(Compilation compilation, Set<EventShortDto> eventShortDtos) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setEvents(eventShortDtos);
        return compilationDto;
    }
}
