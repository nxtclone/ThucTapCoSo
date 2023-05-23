package com.nxt.ttcs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new LoginHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.headers().frameOptions().disable();// FOR H2-CONSOLE
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/images/**","/login","/register","/assets/**").permitAll()
                                //.requestMatchers(PathRequest.toH2Console()).permitAll()// FOR H2-CONSOLE

                                .requestMatchers(
                                        "/hoctap/**",
                                        "/quanlythuviensinhvien/**",
                                        "/tienichkhac/**",
                                        "/dashboard",
                                        "/account/center",
                                        "/account/center/edit",
                                        "/tintuc",
                                        "/dichvumotcuasv",
                                        "/congnosinhvien"
                                ).hasRole("USER")
                                .requestMatchers("/uploadExcel","/xuatbangdiem/**","/danhsachnhomlop","/quanlydiem/**","/quanlysinhvien/**","/themsinhvien/**","/xoasinhvien").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                //.defaultSuccessUrl("/dashboard")
                                .successHandler(myAuthenticationSuccessHandler())
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
    //@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
