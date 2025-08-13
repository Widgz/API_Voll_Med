package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta classe é um controller do Spring que retorna dados (JSON) em vez de páginas HTML
@RequestMapping("/login") // Define a rota base "/login" para os métodos deste controller
public class AutenticacaoController {

    @Autowired // Injeta automaticamente uma instância de AuthenticationManager
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping // Indica que este metodo responde a requisições HTTP POST
    public ResponseEntity efetuarLogin (@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um objeto de autenticação com as credenciais enviadas na requisição
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // Solicita ao AuthenticationManager a autenticação do usuário com as credenciais fornecidas
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Retorna uma resposta HTTP 200 (OK) sem corpo
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}

