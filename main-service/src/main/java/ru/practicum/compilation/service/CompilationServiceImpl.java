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

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepo compilationRepo;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return CompilationMapper.mapToDtoList(compilationRepo
                    .findAll(PageRequest.of(from / size, size)).getContent());
        } else {
           return CompilationMapper.mapToDtoList(compilationRepo
                   .findByPinned(pinned, PageRequest.of(from / size, size)).getContent());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(long compId) {
        return CompilationMapper.mapToDto(compilationRepo.findById(compId)
                .orElseThrow(() -> new NotFoundException(Compilation.class.getName(), compId)));
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getEvents() == null) {
            return CompilationMapper.mapToDto(compilationRepo
                    .save(CompilationMapper.mapToCompilation(newCompilationDto, new ArrayList<>())));
        }

        List<Event> events = eventService.getEventsById(new ArrayList<>(newCompilationDto.getEvents()));
        return CompilationMapper.mapToDto(compilationRepo
                .save(CompilationMapper.mapToCompilation(newCompilationDto, events)));
    }

    @Override
    public void deleteCompilation(long compId) {
        if (compilationRepo.existsById(compId)) {
            compilationRepo.deleteById(compId);
        }
        throw new NotFoundException(Compilation.class.getName(), compId);
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
        return CompilationMapper.mapToDto(compilationRepo.save(compilation));
    }
}
