package com.example.CAR__KIT.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/admin/categories").hasRole("ADMIN")
                                .requestMatchers("/admin/categories/add").hasRole("ADMIN")
                                .requestMatchers("/admin/categories/add").hasRole("ADMIN")
                                .requestMatchers("/admin/categories/delete/**").hasRole("ADMIN")
                                .requestMatchers("/admin/categories/update/**").hasRole("ADMIN")
                                .requestMatchers("/admin/products").hasRole("ADMIN")
                                .requestMatchers("/admin/products/add").hasRole("ADMIN")
                                .requestMatchers("/admin/products/add").hasRole("ADMIN")
                                .requestMatchers("/admin/product/delete/**").hasRole("ADMIN")
                                .requestMatchers("/shop/category/**").hasRole("ADMIN")
                                .requestMatchers("/shop/**").permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/shop")
                                .defaultSuccessUrl("/shop/category/{id}")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}