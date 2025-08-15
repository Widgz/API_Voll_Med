package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    //todos as classes de validacao possuem o mesmo metodo em comum: o metodo validar
    //dessa forma, podemos trabalhar com uma interface para aplica-las de uma vez
    //aqui colocamos que o metodo validar como obrigatorio
    void validar (DadosAgendamentoConsulta dados);

}
