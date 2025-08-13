package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Indica que esta é uma classe de configuração do Spring
@Configuration

// Ativa o módulo de segurança do Spring Security para a aplicação
@EnableWebSecurity
public class SecurityConfigurations {

    // Define um bean do tipo SecurityFilterChain que contém as regras de segurança da aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // Desabilita a proteção contra CSRF (Cross-Site Request Forgery)
                // Em APIs REST stateless, geralmente não é necessário usar CSRF
                .csrf(csrf -> csrf.disable())

                // Define que a aplicação não manterá sessões de usuário no servidor (stateless)
                // Cada requisição deve conter as credenciais/autenticação necessárias
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Constrói e retorna a cadeia de filtros de segurança configurada
                .build();
    }

    @Bean // Indica que o metodo produz um bean que será gerenciado pelo Spring e disponível para injeção
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Retorna uma instância de AuthenticationManager obtida a partir da configuração do Spring Security
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Retorna uma instância de BCryptPasswordEncoder, que aplica o algoritmo BCrypt para criptografar senhas
        return new BCryptPasswordEncoder();
    }
}
