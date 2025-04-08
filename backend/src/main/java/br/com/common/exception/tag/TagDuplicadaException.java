package br.com.common.exception.tag;

import br.com.common.exception.generic.ApiException;

public class TagDuplicadaException extends ApiException {
    public TagDuplicadaException(String value) {
        super("Tag com este nome já existe.", "create.tagRequestDTO.nome", value);
    }
}