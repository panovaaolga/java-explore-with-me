package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import ru.practicum.NotFoundException;
import ru.practicum.compilation.Compilation;
import ru.practicum.compilation.CompilationMapper;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.repository.CompilationRepo;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;

import java.util.List;

@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepo compilationRepo;
    private final EventService eventService;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return CompilationMapper.mapToDtoList(compilationRepo
                    .findAllOrderById(PageRequest.of(from / size, size)).getContent());
        } else {
           return CompilationMapper.mapToDtoList(compilationRepo
                   .findByPinnedOrderById(pinned, PageRequest.of(from / size, size)).getContent());
        }
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return CompilationMapper.mapToDto(compilationRepo.findById(compId)
                .orElseThrow(() -> new NotFoundException(Compilation.class.getName(), compId)));
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
       // List<Event> events = eventService.getEvents() но тогда здесь будет передаваться не лист ивентов,
        // а лист ивентДто, тк метод такой... Надо еще подумать
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {
        if (compilationRepo.existsById(compId)) {
            compilationRepo.deleteById(compId);
        }
        throw new NotFoundException(Compilation.class.getName(), compId);
    }

    @Override
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
            //прописать маппинг айди ивентов в ивенты
        }
        return CompilationMapper.mapToDto(compilationRepo.save(compilation));
    }
}
