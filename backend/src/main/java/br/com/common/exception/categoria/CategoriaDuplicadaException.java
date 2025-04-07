package br.com.common.exception.categoria;

import br.com.common.exception.generic.ApiException;

public class CategoriaDuplicadaException extends ApiException {
    public CategoriaDuplicadaException(String value) {
        super("Categoria com este nome já existe.", "create.categoriaRequestDTO.nome", value);
    }
}