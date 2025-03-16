package br.com.application.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import br.com.domain.model.Log;
import br.com.domain.model.enums.LogLevel;
import br.com.domain.model.enums.OperationType;
import br.com.domain.repository.LogRepository;

@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {

    @Inject
    LogRepository logRepository;

    @GET
    @Path("/dia/{data}")
    public List<Log> getLogsPorDia(@PathParam("data") String data) {
        return logRepository.buscarPorDia(LocalDate.parse(data));
    }

    @GET
    @Path("/mes/{ano}/{mes}")
    public List<Log> getLogsPorMes(@PathParam("ano") int ano, @PathParam("mes") int mes) {
        return logRepository.buscarPorMes(ano, mes);
    }

    @GET
    @Path("/ano/{ano}")
    public List<Log> getLogsPorAno(@PathParam("ano") int ano) {
        return logRepository.buscarPorAno(ano);
    }

    @GET
    @Path("/contar/nivel/{level}")
    public long contarLogsPorNivel(@PathParam("level") LogLevel level) {
        return logRepository.contarPorNivel(level);
    }

    @GET
    @Path("/contar/operacao/{operationType}")
    public long contarLogsPorOperacao(@PathParam("operationType") OperationType operationType) {
        return logRepository.contarPorTipoOperacao(operationType);
    }
}
