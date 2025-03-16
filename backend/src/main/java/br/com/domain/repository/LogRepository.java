package br.com.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

import br.com.domain.model.Log;
import br.com.domain.model.enums.LogLevel;
import br.com.domain.model.enums.OperationType;

@ApplicationScoped
public class LogRepository implements PanacheRepository<Log> {

    public List<Log> buscarPorDia(LocalDate data) {
        return find("DATE(timestamp) = ?1", data).list();
    }

    public List<Log> buscarPorMes(int ano, int mes) {
        return find("YEAR(timestamp) = ?1 AND MONTH(timestamp) = ?2", ano, mes).list();
    }

    public List<Log> buscarPorAno(int ano) {
        return find("YEAR(timestamp) = ?1", ano).list();
    }

    public long contarPorNivel(LogLevel level) {
        return count("level", level);
    }

    public long contarPorTipoOperacao(OperationType operationType) {
        return count("operationType", operationType);
    }
}
