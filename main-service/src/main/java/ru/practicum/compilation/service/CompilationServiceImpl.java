package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.repository.CompilationRepo;

import java.util.List;

@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepo compilationRepo;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        //если лист не найден, то педеаем пустой
        if (pinned == null) {
            return compilationRepo.findAllOrderByTitle(PageRequest.of(from / size, size)).getContent();
        } else {
           return compilationRepo.findByPinnedOrderByTitle(pinned, PageRequest.of(from / size, size)).getContent();
        }
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return compilationRepo.findById(compId).orElseThrow(); //переписать
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {
        if (compilationRepo.existsById(compId)) {
            compilationRepo.deleteById(compId);
        }
        //throw
    }

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
