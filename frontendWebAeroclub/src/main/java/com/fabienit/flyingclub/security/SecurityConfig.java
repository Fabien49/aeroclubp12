package com.fabienit.flyingclub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
            .authenticationProvider(authenticationProvider());
            }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
/*        .antMatchers("/static/**").permitAll()
        .antMatchers("/images/**").permitAll()*/
        .antMatchers("/").permitAll()
        .antMatchers("/profile").hasRole("USER")
        .antMatchers("/resources/**","/static/**","/css/**", "/img/**").permitAll()
        .antMatchers("/reservations").permitAll()
        .antMatchers("/getAvailableAircrafts").permitAll()
        .antMatchers("/aircrafts").permitAll()
        .antMatchers("/aircrafts/id").permitAll()
        .antMatchers("/addAircrafts").permitAll()
        .antMatchers("/Home").permitAll()
        .and()
        .formLogin()
        .loginPage("/connexion").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/disconnect");
    }

/*    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**","/static/**","/css/**", "/images/**");
    }*/

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}