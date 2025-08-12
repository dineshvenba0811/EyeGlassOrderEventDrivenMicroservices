package com.banyan.smartDocuments.AuthenticationService

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

class AuthenticationServiceValidatorTest {
    private val validator = AuthenticationServiceValidator()

    // --- validateUsername -----------------------------------------------------

    @Nested
    inner class ValidateUsername {

        @ParameterizedTest
        @ValueSource(strings = ["abc", "john_doe", "User123", "aaaaaaaaaaaaaaaaaaaa"])
        fun `valid usernames return true`(username: String) {
            assertThat(validator.validateUsername(username)).isTrue()
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = [""])
        fun `invalid usernames return false`(username: String) {
            assertThat(validator.validateUsername(username)).isFalse()
        }
    }

    // --- validatePassword -----------------------------------------------------

    @Nested
    inner class ValidatePassword {

        @ParameterizedTest
        @ValueSource(strings = ["password!", "12345678", "Complex#Pass99", "        8chars"])
        fun `valid passwords greater than 8 return true`(password: String) {
            assertThat(validator.validatePassword(password)).isTrue()
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = ["short", "1234567", "      7"])
        fun `invalid passwords return false`(password: String) {
            assertThat(validator.validatePassword(password)).isFalse()
        }
    }

    // --- validateRoles --------------------------------------------------------

    @Nested
    inner class ValidateRoles {

        @Test
        fun `non-empty roles with non-blank entries return true`() {
            val roles = setOf("USER", "ADMIN")
            assertThat(validator.validateRoles(roles)).isTrue()
        }

        @Test
        fun `empty roles return false`() {
            assertThat(validator.validateRoles(emptySet())).isFalse()
        }

        @Test
        fun `roles containing blank entry return false`() {
            val roles = setOf("USER", " ")
            assertThat(validator.validateRoles(roles)).isFalse()
        }
    }

    // --- validateNewUserRegistration -----------------------------------------

    @Nested
    inner class ValidateNewUserRegistration {

        private fun req(
            username: String = "validUser",
            password: String = "validPass123",
            roles: Set<String> = setOf("USER")
        ) = NewUserRegistrationRequest(
            username = username,
            password = password,
            roles = roles
        )

        @Test
        fun `returns true when all fields are valid`() {
            val request = req()
            assertThat(validator.validateNewUserRegistration(request)).isTrue()
        }

        @Test
        fun `returns false when username invalid`() {
            val request = req(username = "ab") // too short
            assertThat(validator.validateNewUserRegistration(request)).isFalse()
        }

        @Test
        fun `returns false when password invalid`() {
            val request = req(password = "short")
            assertThat(validator.validateNewUserRegistration(request)).isFalse()
        }

        @Test
        fun `returns false when roles invalid`() {
            val request = req(roles = emptySet())
            assertThat(validator.validateNewUserRegistration(request)).isFalse()
        }
    }
}