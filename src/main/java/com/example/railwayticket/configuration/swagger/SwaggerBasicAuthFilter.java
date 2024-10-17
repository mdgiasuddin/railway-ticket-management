package com.example.railwayticket.configuration.swagger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SwaggerBasicAuthFilter extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;

    @Value("${swagger.username}")
    private String swaggerUsername;
    @Value("${swagger.password}")
    private String swaggerPassword;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (isRestrictedUrl(requestUri)) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Basic ")) {
                String[] credentials = extractAndDecodeHeader(authHeader);

                if (isAuthorized(credentials[0], credentials[1])) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            response.setHeader("WWW-Authenticate", "Basic realm=\"Swagger\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isRestrictedUrl(String requestUri) {
        return requestUri.startsWith("/swagger") || requestUri.startsWith("/api-docs") || requestUri.startsWith("/swagger-ui.html");
    }

    private boolean isAuthorized(String username, String password) {
        return username.equals(swaggerUsername) && passwordEncoder.matches(password, swaggerPassword);
    }

    private String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new IOException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new IOException("Invalid basic authentication token");
        }

        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
