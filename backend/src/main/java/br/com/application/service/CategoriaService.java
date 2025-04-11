package br.com.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//import org.apache.kafka.common.errors.ResourceNotFoundException;

import br.com.common.exception.ExceptionLogger;
import br.com.common.exception.categoria.CategoriaDuplicadaException;
import br.com.common.exception.categoria.CategoriaNotFoundException;
import br.com.domain.dto.request.CategoriaRequestDTO;
import br.com.domain.dto.response.CategoriaResponseDTO;
import br.com.domain.mapper.CategoriaMapper;
import br.com.domain.model.Categoria;
import br.com.domain.repository.CategoriaRepository;

@ApplicationScoped
@ExceptionLogger // Ativa o interceptor para capturar exceções e registrar logs
public class CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    Event<Categoria> categoriaEvent;

    public List<CategoriaResponseDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.listAll();
        return categorias.stream()
                .map(CategoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO getCategoriaById(Long id) {
        return categoriaRepository.findByIdOptional(id)
                .map(CategoriaMapper::toResponseDTO)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria com ID " + id + " não encontrada"));
    }

    // Garante que todas as operações dentro do método sejam executadas em uma única
    // transação.
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {

        // Converte o DTO recebido da requisição para uma entidade JPA.
        Categoria categoria = CategoriaMapper.toEntity(categoriaRequestDTO);

        try {
            // Persiste a entidade no banco e força a execução imediata.
            categoria.persistAndFlush();

            // Se persistiu com sucesso, retorna o DTO de resposta.
            return CategoriaMapper.toResponseDTO(categoria);

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
                    throw new CategoriaDuplicadaException("Categoria com este nome já existe.");
                }
                cause = cause.getCause(); // Vai para a causa anterior na cadeia de exceções.
            }

            // Se não for uma violação de restrição, relança a exceção original.
            throw e;
        }
    }

    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria existingCategoria = categoriaRepository.findByIdOptional(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria com ID " + id + " não encontrada"));

        existingCategoria.setNome(categoriaRequestDTO.getNome());

        try {
            // Persiste e força o flush, para que exceções de integridade apareçam
            // imediatamente
            categoriaRepository.persistAndFlush(existingCategoria);

            // Dispara evento informando que houve atualização
            categoriaEvent.fire(existingCategoria);

            return CategoriaMapper.toResponseDTO(existingCategoria);
        } catch (PersistenceException e) {
            // Verifica se há alguma causa do tipo ConstraintViolationException
            Throwable cause = e;
            while (cause != null) {
                if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
                    throw new CategoriaDuplicadaException("Categoria com este nome já existe.");
                }
                cause = cause.getCause();
            }

            // Se não for violação de restrição, relança a exceção original
            throw e;
        }
    }

    @Transactional
    public boolean deleteCategoria(Long id) {
        return categoriaRepository.deleteById(id);
    }
}
