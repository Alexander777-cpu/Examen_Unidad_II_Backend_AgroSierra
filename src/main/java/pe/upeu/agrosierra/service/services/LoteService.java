package pe.upeu.agrosierra.service.services;

import pe.upeu.agrosierra.dto.LoteRequestDTO;
import pe.upeu.agrosierra.dto.LoteResponseDTO;
import pe.upeu.agrosierra.dto.PaginaResponseDTO;

public interface LoteService {

    PaginaResponseDTO<LoteResponseDTO> listar(String valle, int pagina, int tamanio, String ordenarPor, String direccion);

    LoteResponseDTO buscar(Long id);

    LoteResponseDTO cerrarCampania(Long id);

    LoteResponseDTO crear(LoteRequestDTO request);
}