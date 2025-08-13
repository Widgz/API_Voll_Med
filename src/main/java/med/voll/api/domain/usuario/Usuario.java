package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios") // Define que esta entidade será mapeada para a tabela "usuarios"
@Entity(name = "Usuario") // Define que esta classe é uma entidade JPA com o nome "Usuario"
@Getter // Gera automaticamente getters para todos os atributos
@NoArgsConstructor // Gera um construtor sem argumentos (obrigatório para JPA)
@AllArgsConstructor // Gera um construtor com todos os argumentos
@EqualsAndHashCode(of = "id") // Gera equals() e hashCode() baseados apenas no campo "id"
public class Usuario implements UserDetails { // Implementa UserDetails para integração com Spring Security

    @Id // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID pelo banco (auto incremento)
    private Long id;

    private String login; // Nome de usuário
    private String senha; // Senha criptografada

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define as permissões do usuário — aqui todos terão a role "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Retorna a senha (usada pelo Spring Security)
        return senha;
    }

    @Override
    public String getUsername() {
        // Retorna o login (nome de usuário usado para autenticação)
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Retorna true, indicando que a conta não expirou
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Retorna true, indicando que a conta não está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Retorna true, indicando que as credenciais não expiraram
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Retorna true, indicando que o usuário está ativo
        return true;
    }
}
