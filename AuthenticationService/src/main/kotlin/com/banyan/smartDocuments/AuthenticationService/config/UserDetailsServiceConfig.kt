package com.banyan.smartDocuments.AuthenticationService.config

import com.banyan.smartDocuments.AuthenticationService.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/** * UserDetailsServiceConfig is a configuration class that sets up the UserDetailsService
 * for loading user-specific data. It uses the UserRepository to fetch user details from the database.
 * It also provides a PasswordEncoder bean for encoding passwords.
 */
@Configuration
class UserDetailsServiceConfig(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService = UserDetailsService { username ->
        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("User not found")
        org.springframework.security.core.userdetails.User
            .withUsername(user.username)
            .password(user.password)
            .roles(*user.roles.map { it.roleName }.toTypedArray())// Pass roles to Spring's User object
            .build()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}