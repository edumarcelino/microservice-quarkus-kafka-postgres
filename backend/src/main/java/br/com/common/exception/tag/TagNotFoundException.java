package br.com.common.exception.tag;

import br.com.common.exception.generic.ApiException;

public class TagNotFoundException extends ApiException {
    public TagNotFoundException(String value) {
        super("Tag com ID " + value + " não encontrada", "update.tagRequestDTO.nome", value);
    }
}

