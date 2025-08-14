package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
// Indica que a classe é um serviço gerenciado pelo Spring
@Service
public class TokenService {

    // Realiza a leitura da propriedade definida no arquivo aplication.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Metodo público para gerar um token JWT para um usuário específico
    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura HMAC256 com uma chave secreta
            var algoritmo = Algorithm.HMAC256(secret);

            // Cria e retorna o token JWT configurado
            return JWT.create()
                    // Define o emissor do token
                    .withIssuer("API Voll.med")
                    // Define o assunto do token (usuário)
                    .withSubject(usuario.getLogin())
                    // Define a data de expiração do token
                    .withExpiresAt(dataExpiracao())
                    // Assina o token com o algoritmo definido
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            // Lança exceção em caso de erro na criação do token
            throw new RuntimeException("erro ao gerar token JWT", exception);
        }
    }

    //metodo para verificacao e validacao do token enviado pela requisicao
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    // Metodo privado para calcular a data de expiração do token
    private Instant dataExpiracao() {
        // Define a expiração como 2 horas a partir do momento atual, considerando o fuso -03:00
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
