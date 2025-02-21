package com.rapidticket.auth.utils.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoUtils {
    private final PasswordEncoder passwordEncoder;

    /**
     * Hashes a password using BCrypt.
     * @param password The raw password to encrypt.
     * @return The hashed password.
     */
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Verifies a raw password against the hashed password.
     * @param rawPassword The plain text password.
     * @param hashedPassword The stored hashed password.
     * @return True if the password matches, false otherwise.
     */
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
