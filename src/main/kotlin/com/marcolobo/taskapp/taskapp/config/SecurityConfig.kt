package com.marcolobo.taskapp.taskapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // Usuário em memória fixo
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): InMemoryUserDetailsManager {
        val user = User.withUsername("malobo")
            .password(passwordEncoder.encode("233234"))
            .roles("USER") // ou ADMIN, se quiser
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // desabilita CSRF para API
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/users/**").permitAll() // login/registro liberados
                    .requestMatchers("/api/tasks/**").authenticated() // API de tarefas exige login
                    .requestMatchers("/hello").authenticated() // endpoint de teste
                    .anyRequest().authenticated()
            }
            .httpBasic {} // Autenticação básica
            .formLogin { it.disable() } // desabilita form login padrão

        return http.build()
    }
}

// Endpoint simples para teste
@RestController
class TestController {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, malobo! Você está autenticado ✅"
    }
}
