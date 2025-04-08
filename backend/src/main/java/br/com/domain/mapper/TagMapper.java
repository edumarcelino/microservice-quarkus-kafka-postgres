package br.com.domain.mapper;

import br.com.domain.dto.request.TagRequestDTO;
import br.com.domain.dto.response.TagResponseDTO;
import br.com.domain.model.Tag;

public class TagMapper {
    // Mapeia de TagRequestDTO para Tag (entidade)
    public static Tag toEntity(TagRequestDTO tagRequestDTO) {
        Tag tag = new Tag();
        tag.setNome(tagRequestDTO.getNome());
        return tag;
    }

    // Mapeia de Tag (entidade) para TagResponseDTO
    public static TagResponseDTO toResponseDTO(Tag tag) {
        return new TagResponseDTO(tag.id, tag.getNome());
    }
}
