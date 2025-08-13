package com.banyan.smartDocuments.AuthenticationService.util

import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

/**
 * JwtUtil** is a utility class for handling JSON Web Tokens (JWT).
 * It provides methods to generate, validate, and extract information from JWTs.
 */
@Component
class JwtUtil {
    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationTime = 60 * 60 * 1000 // 1 hour
    private val logger = LoggerFactory.getLogger(JwtUtil::class.java)

    fun generateToken(username: String): String {
        logger.info("Generating token for user $username")
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        logger.info("Validating token")
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            logger.error("Invalid JWT token: {}", e.message)
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        logger.info("Getting username from token")
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }
}