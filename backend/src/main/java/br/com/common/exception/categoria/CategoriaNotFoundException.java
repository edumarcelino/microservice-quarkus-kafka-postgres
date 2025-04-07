package br.com.common.exception.categoria;

import br.com.common.exception.generic.ApiException;

public class CategoriaNotFoundException extends ApiException {
    public CategoriaNotFoundException(String value) {
        super("Categoria com ID " + value + " não encontrada", "update.categoriaRequestDTO.nome", value);
    }
}

