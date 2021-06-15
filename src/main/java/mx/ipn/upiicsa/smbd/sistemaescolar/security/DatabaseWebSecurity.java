package mx.ipn.upiicsa.smbd.sistemaescolar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT usuario, password, estatus FROM perfiles WHERE usuario = ?")
                .authoritiesByUsernameQuery("SELECT usuario, rol FROM perfiles, roles WHERE perfiles.id_rol = roles.id_rol AND usuario = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bcrypt/{txt}").permitAll()
                .antMatchers("/administrador/**").hasAnyAuthority("Administrador")
                .antMatchers("/profesor/**").hasAnyAuthority("Profesor")
                .antMatchers("/estudiante/**").hasAnyAuthority("Alumno")
                .anyRequest().authenticated()
                .and().formLogin().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
