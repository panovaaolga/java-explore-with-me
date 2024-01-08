package ru.practicum.compilation.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    @PostMapping
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return null;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {

    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
