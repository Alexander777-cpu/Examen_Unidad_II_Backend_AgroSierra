package pe.upeu.agrosierra.service.services;

import pe.upeu.agrosierra.dto.LaborRequestDTO;
import pe.upeu.agrosierra.dto.LaborResponseDTO;
import pe.upeu.agrosierra.dto.PaginaResponseDTO;

public interface LaborService {

    PaginaResponseDTO<LaborResponseDTO> listarPorLote(Long loteId, int pagina, int tamanio, String ordenarPor, String direccion);

    LaborResponseDTO buscar(Long id);

    LaborResponseDTO registrar(LaborRequestDTO request, String usernameAutenticado);

    LaborResponseDTO corregir(Long id, LaborRequestDTO request, String usernameAutenticado);

    void eliminar(Long id);
}