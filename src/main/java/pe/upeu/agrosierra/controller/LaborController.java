package pe.upeu.agrosierra.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.upeu.agrosierra.dto.LaborRequestDTO;
import pe.upeu.agrosierra.dto.LaborResponseDTO;
import pe.upeu.agrosierra.service.services.LaborService;

@RestController
@RequestMapping("/api/v1/labores")
public class LaborController {

    private final LaborService laborService;

    public LaborController(LaborService laborService) {
        this.laborService = laborService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaborResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(laborService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<LaborResponseDTO> registrar(
            @Valid @RequestBody LaborRequestDTO request,
            Authentication authentication) {

        String username = authentication != null ? authentication.getName() : null;
        LaborResponseDTO response = laborService.registrar(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaborResponseDTO> corregir(
            @PathVariable Long id,
            @Valid @RequestBody LaborRequestDTO request,
            Authentication authentication) {

        String username = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(laborService.corregir(id, request, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        laborService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}