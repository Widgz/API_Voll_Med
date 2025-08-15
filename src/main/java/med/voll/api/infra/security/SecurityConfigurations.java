package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Indica que esta é uma classe de configuração do Spring
@Configuration

// Ativa o módulo de segurança do Spring Security para a aplicação
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    // Define um bean do tipo SecurityFilterChain que contém as regras de segurança da aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(csrf -> csrf.disable())
                        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(req -> {
                            //permite requisições para a URL /login mesmo que o usuário nao esteja logado
                            req.requestMatchers("/login").permitAll();
                            //os astericos duplos indicam que qualquer subendereco esta sendo considerado
                            //liberacao das URLs para implementacao do SpringDoc
                            req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                            //para as demais requisições o usuario deve estar logado
                            req.anyRequest().authenticated();
                        })
                        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
