package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.compilation.Compilation;
import ru.practicum.compilation.CompilationMapper;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.repository.CompilationRepo;
import ru.practicum.event.service.EventService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepo compilationRepo;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        List<CompilationDto> compilationDtos = new ArrayList<>();
        if (pinned == null) {
            if (!compilationRepo
                    .findAll(PageRequest.of(from / size, size)).getContent().isEmpty()) {
                for (Compilation c: compilationRepo
                             .findAll(PageRequest.of(from / size, size)).getContent()) {
                    compilationDtos.add(CompilationMapper.mapToDto(c, eventService.getShortDtoSet(c.getEvents())));
                }
            }
        } else {
            if (!compilationRepo
                    .findByPinned(pinned, PageRequest.of(from / size, size)).getContent().isEmpty()) {
                for (Compilation c : compilationRepo
                        .findByPinned(pinned, PageRequest.of(from / size, size)).getContent()) {
                    compilationDtos.add(CompilationMapper.mapToDto(c, eventService.getShortDtoSet(c.getEvents())));
                }
            }
        }
        return compilationDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(long compId) {
        Compilation compilation = compilationRepo.findById(compId)
                .orElseThrow(() -> new NotFoundException(Compilation.class.getName(), compId));
        Set<Event> eventSet = compilation.getEvents();
        return CompilationMapper.mapToDto(compilation, eventService.getShortDtoSet(eventSet));
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getEvents() == null) {
            return CompilationMapper.mapToDto(compilationRepo
                    .save(CompilationMapper.mapToCompilation(newCompilationDto, new ArrayList<>())), new HashSet<>());
        }

        List<Event> events = eventService.getEventsById(new ArrayList<>(newCompilationDto.getEvents()));
        return CompilationMapper.mapToDto(compilationRepo
                .save(CompilationMapper.mapToCompilation(newCompilationDto, events)),
                eventService.getShortDtoSet(new HashSet<>(events)));
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepo.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepo.findById(compId)
                .orElseThrow(() -> new NotFoundException(Compilation.class.getName(), compId));
        if (updateCompilationRequest.getPinned() != null &&
                updateCompilationRequest.getPinned() != compilation.isPinned()) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null &&
                !updateCompilationRequest.getTitle().equals(compilation.getTitle())) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getEvents() != null) {
            compilation.setEvents(new HashSet<>(eventService
                    .getEventsById(new ArrayList<>(updateCompilationRequest.getEvents()))));
        }
        return CompilationMapper.mapToDto(compilationRepo.save(compilation),
                eventService.getShortDtoSet(compilation.getEvents()));
    }
}
