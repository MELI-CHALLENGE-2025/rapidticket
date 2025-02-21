package com.rapidticket.function.utils.security;

import com.rapidticket.function.response.Response;
import com.rapidticket.function.utils.enums.EnumRoleUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtils jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/v1/api-docs") ||
                requestURI.startsWith("/api/v1/swagger-ui") ||
                requestURI.startsWith("/api/v1/swagger-ui-custom.html") ||
                requestURI.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.extractAllClaims(token);
            String username = claims.getSubject();

            Object rolesObject = claims.get("roles");
            List<String> roles = (rolesObject instanceof List<?> list)
                    ? list.stream().filter(String.class::isInstance).map(String.class::cast).toList()
                    : List.of();

            if (roles.contains(EnumRoleUser.CUSTOMER.name())) {
                sendForbiddenResponse(response);
                return;
            }

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }

    /**
     * Sends a 401 Unauthorized error response when the token is missing or invalid.
     */
    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        Response<Object> errorResponse = new Response<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication is required to access this resource.",
                "Missing or invalid authentication token.",
                "AUTH_401",
                "https://docs.rapidticket.com/errors/AUTH_401",
                null
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    /**
     * Sends a 403 Forbidden error response when the user lacks the required permissions.
     */
    private void sendForbiddenResponse(HttpServletResponse response) throws IOException {
        Response<Object> errorResponse = new Response<>(
                HttpStatus.FORBIDDEN.value(),
                "You do not have permission to access this resource.",
                "User does not have sufficient permissions.",
                "AUTH_403",
                "https://docs.rapidticket.com/errors/AUTH_403",
                null
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
