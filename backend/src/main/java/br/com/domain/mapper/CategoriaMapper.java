package br.com.domain.mapper;

import br.com.domain.dto.request.CategoriaRequestDTO;
import br.com.domain.dto.response.CategoriaResponseDTO;
import br.com.domain.model.Categoria;

public class CategoriaMapper {
    // Mapeia de CategoriaRequestDTO para Categoria (entidade)
    public static Categoria toEntity(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.getNome());
        return categoria;
    }

    // Mapeia de Categoria (entidade) para CategoriaResponseDTO
    public static CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.id, categoria.getNome());
    }
}
