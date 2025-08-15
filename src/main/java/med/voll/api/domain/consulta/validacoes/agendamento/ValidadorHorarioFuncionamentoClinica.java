package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();

        //verifica se o dia da consulta está sendo agendado no domingo
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        //verifica se o horario enviado para agendamento é antes do horario de expediente da clinica
        var antesDaExpedienteClinica = dataConsulta.getHour() < 7;

        //verifica se o horario enviado para agendamento é após horario de expediente da clinica
        var depoisDoExpedienteClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaExpedienteClinica || depoisDoExpedienteClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }

    }
}
