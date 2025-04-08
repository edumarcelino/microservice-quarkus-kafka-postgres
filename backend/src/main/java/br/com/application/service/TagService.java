package br.com.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import br.com.common.exception.ExceptionLogger;
import br.com.common.exception.tag.TagDuplicadaException;
import br.com.common.exception.tag.TagNotFoundException;
import br.com.domain.dto.request.TagRequestDTO;
import br.com.domain.dto.response.TagResponseDTO;
import br.com.domain.mapper.TagMapper;
import br.com.domain.model.Tag;
import br.com.domain.repository.TagRepository;

@ApplicationScoped
@ExceptionLogger // Ativa o interceptor para capturar exceções e registrar logs
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Inject
    Event<Tag> tagEvent;

    public List<TagResponseDTO> getAllTags() {
        List<Tag> tags = tagRepository.listAll();
        return tags.stream()
                .map(TagMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TagResponseDTO getTagById(Long id) {
        return tagRepository.findByIdOptional(id)
                .map(TagMapper::toResponseDTO)
                .orElseThrow(() -> new TagNotFoundException("Tag com ID " + id + " não encontrada"));
    }

    // Garante que todas as operações dentro do método sejam executadas em uma única
    // transação.
    @Transactional
    public TagResponseDTO create(TagRequestDTO tagRequestDTO) {

        // Converte o DTO recebido da requisição para uma entidade JPA.
        Tag tag = TagMapper.toEntity(tagRequestDTO);

        try {
            // Persiste a entidade no banco e força a execução imediata.
            tag.persistAndFlush();

            // Se persistiu com sucesso, retorna o DTO de resposta.
            return TagMapper.toResponseDTO(tag);

        } catch (PersistenceException e) {
            // Se ocorrer uma exceção de persistência (ex: violação de chave única), entra
            // aqui.

            // Inicia a análise da cadeia de causas da exceção.
            Throwable cause = e;
            while (cause != null) {
                // Verifica se alguma das causas é uma ConstraintViolationException do
                // Hibernate,
                // que indica violação de restrições do banco (ex: chave única).
                if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
                    // Se for, lança uma exceção customizada da aplicação, que será mapeada pelo
                    // ExceptionMapper.
                    throw new TagDuplicadaException("Tag com este nome já existe.");
                }
                cause = cause.getCause(); // Vai para a causa anterior na cadeia de exceções.
            }

            // Se não for uma violação de restrição, relança a exceção original.
            throw e;
        }
    }

    @Transactional
    public TagResponseDTO updateTag(Long id, TagRequestDTO tagRequestDTO) {
        Tag existingTag = tagRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag com ID " + id + " não encontrada"));

        existingTag.setNome(tagRequestDTO.getNome());

        try {
            // Persiste e força o flush, para que exceções de integridade apareçam
            // imediatamente
            tagRepository.persistAndFlush(existingTag);

            // Dispara evento informando que houve atualização
            tagEvent.fire(existingTag);

            return TagMapper.toResponseDTO(existingTag);
        } catch (PersistenceException e) {
            // Verifica se há alguma causa do tipo ConstraintViolationException
            Throwable cause = e;
            while (cause != null) {
                if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
                    throw new TagDuplicadaException("Tag com este nome já existe.");
                }
                cause = cause.getCause();
            }

            // Se não for violação de restrição, relança a exceção original
            throw e;
        }
    }

    @Transactional
    public boolean deleteTag(Long id) {
        return tagRepository.deleteById(id);
    }
}
