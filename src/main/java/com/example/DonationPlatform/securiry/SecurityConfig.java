
package com.example.DonationPlatform.securiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Autowired
    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/{id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/putMoney/{sum}").permitAll()
                .antMatchers(HttpMethod.GET, "/user/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/allCardsOfUser/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/user/allTransactionForUser/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/user/allTransactionForAdmin/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/update").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/updateByAdmin").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/transaction/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/transaction").permitAll()

                .antMatchers(HttpMethod.POST, "/cards").permitAll()
                .antMatchers(HttpMethod.DELETE, "/cards").permitAll()
                .antMatchers(HttpMethod.GET, "/cards/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/actuator/{id}").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .userDetailsService(customUserDetailService)
                .httpBasic()
                .and() //указываем тип секурити
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
