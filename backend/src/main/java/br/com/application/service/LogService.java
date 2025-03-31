package br.com.application.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.jboss.logging.Logger;

import br.com.domain.model.Log;
import br.com.domain.model.enums.LogLevel;
import br.com.domain.model.enums.OperationType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class LogService {

    private static final Logger LOG = Logger.getLogger(LogService.class);

    @Inject
    SecurityContext securityContext;

    @Transactional
    public void logInfo(String message, OperationType operationType) {
        log(LogLevel.INFO, message, null, operationType);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void logError(String message, Exception exception, OperationType operationType) {
        log(LogLevel.ERROR, message, exception, operationType);
        System.out.println(LogLevel.ERROR);
        System.out.println(message);
        System.out.println(exception);
        System.out.println(operationType);
    }

    @Transactional
    public void logWarn(String message, OperationType operationType) {
        log(LogLevel.WARN, message, null, operationType);
    }

    @Transactional
    public void logDebug(String message, OperationType operationType) {
        log(LogLevel.DEBUG, message, null, operationType);
    }

    @Transactional
    public void log(LogLevel level, String message, Exception exception, OperationType operationType) {
        saveLog(level, message, exception != null ? exception.getMessage() : null, getStackTrace(exception),
                operationType);
    }

    private void saveLog(LogLevel level, String message, String exceptionMessage, String exceptionStackTrace,
            OperationType operationType) {
        Log log = new Log();
        log.level = level;
        log.message = message;
        log.exceptionMessage = exceptionMessage;
        log.exceptionStackTrace = exceptionStackTrace;
        log.operationType = operationType;
        log.logUser = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName()
                : "unknown";
        log.persistAndFlush();

        System.out.println(log);

        // Log no console conforme o nível
        switch (level) {
            case ERROR -> LOG.error(message, exceptionMessage == null ? null : new RuntimeException(exceptionMessage));
            case WARN -> LOG.warn(message);
            case INFO -> LOG.info(message);
            case DEBUG -> LOG.debug(message);
        }
    }

    private String getStackTrace(Exception exception) {
        if (exception == null)
            return null;
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
