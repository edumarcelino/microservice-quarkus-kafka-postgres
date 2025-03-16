package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.domain.model.enums.LogLevel;
import br.com.domain.model.enums.OperationType;

@Entity
public class Log extends PanacheEntity {

    @Column(name = "log_user", nullable = false)  
    public String logUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "de_level", nullable = false)
    public LogLevel level;

    @Column(name = "de_message", columnDefinition = "TEXT", nullable = false)
    public String message;

    @Column(columnDefinition = "TEXT")
    public String exceptionMessage;

    @Column(columnDefinition = "TEXT")
    public String exceptionStackTrace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OperationType operationType;

    @CreationTimestamp
    public LocalDateTime timestamp;

}
