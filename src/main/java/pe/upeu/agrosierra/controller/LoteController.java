package pe.upeu.agrosierra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upeu.agrosierra.dto.LaborResponseDTO;
import pe.upeu.agrosierra.dto.LoteResponseDTO;
import pe.upeu.agrosierra.dto.PaginaResponseDTO;
import pe.upeu.agrosierra.service.services.LaborService;
import pe.upeu.agrosierra.service.services.LoteService;

@RestController
@RequestMapping("/api/v1/lotes")
public class LoteController {

    private final LoteService loteService;
    private final LaborService laborService;

    public LoteController(LoteService loteService, LaborService laborService) {
        this.loteService = loteService;
        this.laborService = laborService;
    }

    @GetMapping
    public ResponseEntity<PaginaResponseDTO<LoteResponseDTO>> listar(
            @RequestParam(required = false) String valle,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanio,
            @RequestParam(required = false, defaultValue = "id") String ordenarPor,
            @RequestParam(required = false, defaultValue = "asc") String direccion) {

        return ResponseEntity.ok(loteService.listar(valle, pagina, tamanio, ordenarPor, direccion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.buscar(id));
    }

    @GetMapping("/{id}/labores")
    public ResponseEntity<PaginaResponseDTO<LaborResponseDTO>> listarLabores(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanio,
            @RequestParam(required = false, defaultValue = "fecha") String ordenarPor,
            @RequestParam(required = false, defaultValue = "desc") String direccion) {

        return ResponseEntity.ok(laborService.listarPorLote(id, pagina, tamanio, ordenarPor, direccion));
    }

    @PostMapping("/{id}/cerrar-campania")
    public ResponseEntity<LoteResponseDTO> cerrarCampania(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.cerrarCampania(id));
    }
}