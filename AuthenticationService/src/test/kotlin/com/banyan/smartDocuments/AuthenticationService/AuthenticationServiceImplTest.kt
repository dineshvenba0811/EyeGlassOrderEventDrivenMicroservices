package com.banyan.smartDocuments.AuthenticationService

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import com.banyan.smartDocuments.AuthenticationService.model.Role
import com.banyan.smartDocuments.AuthenticationService.model.User
import com.banyan.smartDocuments.AuthenticationService.repository.RoleRepository
import com.banyan.smartDocuments.AuthenticationService.repository.UserRepository
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import io.mockk.*

class AuthenticationServiceImplTest {
    private lateinit var userRepository: UserRepository
    private lateinit var roleRepository: RoleRepository
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var service: AuthenticationServiceImpl

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        roleRepository = mockk()
        passwordEncoder = mockk()
        service = AuthenticationServiceImpl(userRepository, roleRepository, passwordEncoder)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `newUserRegistration - success encodes password, resolves roles, saves and returns message`() {
        // Arrange
        val request = NewUserRegistrationRequest(
            username = "newuser",
            password = "PlainPass123",
            roles = setOf("USER", "ADMIN")
        )

        every { userRepository.findByUsername("newuser") } returns null
        every { passwordEncoder.encode("PlainPass123") } returns "ENC_PlainPass123"

        // Model Role object – if your Role is a class in your model, you can also instantiate it directly.
        val roleUser = mockk<com.banyan.smartDocuments.AuthenticationService.model.Role>()
        val roleAdmin = mockk<com.banyan.smartDocuments.AuthenticationService.model.Role>()
        every { roleRepository.findByRoleName("USER") } returns roleUser
        every { roleRepository.findByRoleName("ADMIN") } returns roleAdmin

        val savedUserSlot = slot<User>()
        every { userRepository.save(capture(savedUserSlot)) } answers { savedUserSlot.captured }

        // Act
        val message = service.newUserRegistration(request)

        // Assert
        assertThat(message).contains("User newuser registered successfully")
        assertThat(savedUserSlot.captured.username).isEqualTo("newuser")
        assertThat(savedUserSlot.captured.password).isEqualTo("ENC_PlainPass123")
        assertThat(savedUserSlot.captured.roles).containsExactlyInAnyOrder(roleUser, roleAdmin)

        verify(exactly = 1) { userRepository.findByUsername("newuser") }
        verify(exactly = 1) { passwordEncoder.encode("PlainPass123") }
        verify(exactly = 1) { roleRepository.findByRoleName("USER") }
        verify(exactly = 1) { roleRepository.findByRoleName("ADMIN") }
        verify(exactly = 1) { userRepository.save(any()) }
        confirmVerified(userRepository, roleRepository, passwordEncoder)
    }

    @Test
    fun `newUserRegistration - throws when user already exists`() {
        // Arrange
        val request = NewUserRegistrationRequest(
            username = "exists",
            password = "whatever",
            roles = setOf("USER")
        )

        val existing = mockk<User>()
        every { userRepository.findByUsername("exists") } returns existing

        // Act + Assert
        val ex = assertThrows<RuntimeException> {
            service.newUserRegistration(request)
        }
        assertThat(ex.message).contains("User with username exists already exists")

        // Verify only the existence check was called; nothing else
        verify(exactly = 1) { userRepository.findByUsername("exists") }
        verify(exactly = 0) { passwordEncoder.encode(any()) }
        verify(exactly = 0) { roleRepository.findByRoleName(any()) }
        verify(exactly = 0) { userRepository.save(any()) }

        confirmVerified(userRepository, roleRepository, passwordEncoder)
    }

    @Test
    fun `newUserRegistration - throws when a role is not found`() {
        val request = NewUserRegistrationRequest(
            username = "newuser2",
            password = "Pass12345",
            roles = setOf("USER", "MISSING_ROLE")
        )

        every { userRepository.findByUsername("newuser2") } returns null
        every { passwordEncoder.encode("Pass12345") } returns "ENC_Pass12345"
        val roleUser = mockk<Role>()
        every { roleRepository.findByRoleName("USER") } returns roleUser
        every { roleRepository.findByRoleName("MISSING_ROLE") } returns null

        val ex = assertThrows<RuntimeException> {
            service.newUserRegistration(request)
        }
        assertThat(ex.message).contains("Role MISSING_ROLE not found")

        verify(exactly = 1) { userRepository.findByUsername("newuser2") }
        verify(exactly = 1) { passwordEncoder.encode("Pass12345") }
        verify(exactly = 1) { roleRepository.findByRoleName("USER") }
        verify(exactly = 1) { roleRepository.findByRoleName("MISSING_ROLE") }
        verify(exactly = 0) { userRepository.save(any()) }
        confirmVerified(userRepository, roleRepository, passwordEncoder)
    }
}