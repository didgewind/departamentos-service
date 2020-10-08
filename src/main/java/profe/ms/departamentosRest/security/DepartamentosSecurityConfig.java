package profe.ms.departamentosRest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@EnableWebSecurity
public class DepartamentosSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
          .and()
          .x509()
            .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
            .userDetailsService(userDetailsService());
    }
 
    @Bean
    public UserDetailsService userDetailsService() {
        return (username -> {
        	/* Si queremos autorizar a cualquiera que tenga el certificado */
            if (username != null) {
                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            }
            /* Si queremos discriminar por usuario (CN) */
        	/*if (username.equals("Bob")) {
                return new User(username, "", 
                  AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_USER"));
            }*/
            throw new UsernameNotFoundException("User not found!");
        });
    }

}