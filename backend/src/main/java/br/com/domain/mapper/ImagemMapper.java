package br.com.domain.mapper;



import br.com.domain.dto.request.ImagemRequesDTO;
import br.com.domain.dto.response.ImagemResponseDTO;
import br.com.domain.model.Imagem;

public class ImagemMapper {

    public static Imagem toEntity(ImagemRequesDTO dto) {
        if (dto == null) return null;

        Imagem imagem = new Imagem();
        imagem.setCid(dto.getCid());
        imagem.setNomeArquivo(dto.getNomeArquivo());
        imagem.setLegenda(dto.getLegenda());
        imagem.setBase64(dto.getBase64());
        return imagem;
    }

    public static ImagemResponseDTO toResponseDTO(Imagem imagem) {
        if (imagem == null) return null;

        String url = "/imagens/" + imagem.getNomeArquivo(); // ajuste se necessário
        return new ImagemResponseDTO(
            imagem.id,
            imagem.getNomeArquivo(),
            imagem.getLegenda(),
            url
        );
    }
}

