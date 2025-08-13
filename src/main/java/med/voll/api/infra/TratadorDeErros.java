package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Anotação para controladores de erros atrelado ao Spring
//Dessa forma, evita-se a utilização de blocos de Try-Catch, que só funcionam nos métodos aplicados
@RestControllerAdvice
public class TratadorDeErros {

    //A anotação abaixo determina em qual momento o Spring aplicará o metodo
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

}
