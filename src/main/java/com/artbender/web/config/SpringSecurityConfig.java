package com.artbender.web.config;

import com.artbender.core.constants.GameConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Spring Security config for handle URIs and which URIs are permitted and who can access them.
 * Configuration information about InMemoryUsers
 *
 * @author Artsiom Leuchanka
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**", "/game", "/logout").hasAnyRole(GameConstants.ROLES)
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin().permitAll().defaultSuccessUrl("/game");
        http.sessionManagement().maximumSessions(1);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username(GameConstants.SOUTH_PLAYER_NAME)
                        .password(passwordEncoder().encode(GameConstants.SOUTH_PLAYER_NAME))
                        .roles(GameConstants.ROLES)
                        .build(),
                User.builder()
                        .username(GameConstants.NORTH_PLAYER_NAME)
                        .password(passwordEncoder().encode(GameConstants.NORTH_PLAYER_NAME))
                        .roles(GameConstants.ROLES)
                        .build()
        );
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**", "/css/**", "/js/**",
                        "/images/**", "/webjars/**");
    }
}
