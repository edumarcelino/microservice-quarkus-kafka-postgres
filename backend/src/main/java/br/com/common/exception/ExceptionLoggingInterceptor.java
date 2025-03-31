package br.com.common.exception;

import br.com.application.service.LogService;
import br.com.domain.model.enums.OperationType;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.PersistenceException;

@Interceptor
@ExceptionLogger
@Priority(Interceptor.Priority.APPLICATION) // Prioridade de aplicação do interceptor
public class ExceptionLoggingInterceptor {

    @Inject
    LogService logService;

    @AroundInvoke
    public Object logExceptions(InvocationContext context) throws Exception {
        try {
            // Continua a execução do método interceptado
            return context.proceed();
        } catch (WeakPasswordException e) {
            logService.logError("Erro ao criar usuário: Senha fraca", e, OperationType.CREATE_USER);
            throw e; // Re-lança a exceção para que o fluxo continue
        } catch (InvalidEmailException e) {
            logService.logError("Erro ao criar usuário: E-mail incorreto", e, OperationType.CREATE_USER);
            throw e;
        } catch (PersistenceException e) {
            logService.logError("Erro no banco de dados", e, OperationType.DATABASE);
            throw e;
        } catch (Exception e) {
            logService.logError("Erro inesperado", e, OperationType.GENERAL);
            throw e;
        }
    }
}