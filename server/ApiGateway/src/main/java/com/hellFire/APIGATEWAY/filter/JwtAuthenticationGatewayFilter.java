package com.hellFire.APIGATEWAY.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies JWT at the gateway for all non-public routes.
 * Public: POST /auth/login, POST /auth/register/*, GET /auth/industries, GET /auth/industries/search, OPTIONS.
 */
@Component
public class JwtAuthenticationGatewayFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

    @Value("${jwt.secret:my-super-secret-key-my-super-secret-key}")
    private String secret;

    private static final String UNAUTHORIZED_JSON = "{\"success\":false,\"message\":\"Unauthorized\",\"errorCode\":\"UNAUTHORIZED\"}";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = request.getMethod().name();

        if (isPublic(path, method)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null || token.isBlank()) {
            return unauthorized(exchange.getResponse());
        }

        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return unauthorized(exchange.getResponse());
            }
            ServerHttpRequest mutatedRequest = addUserHeaders(exchange.getRequest(), claims);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            return unauthorized(exchange.getResponse());
        }
    }

    private boolean isPublic(String path, String method) {
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        if ("POST".equals(method) && "/auth/login".equals(path)) {
            return true;
        }
        if ("POST".equals(method) && path.startsWith("/auth/register/")) {
            return true;
        }
        if ("GET".equals(method) && ("/auth/industries".equals(path) || "/auth/industries/search".equals(path))) {
            return true;
        }
        // Optional: uncomment to allow GET get-all-jobs without JWT (dev only)
        // if ("GET".equals(method) && "/jobs/get-all-jobs".equals(path)) {
        //     return true;
        // }
        return false;
    }

    /** Add user claims as headers so downstream services (e.g. JobService) can set createdBy, tenantId, etc. */
    private ServerHttpRequest addUserHeaders(ServerHttpRequest request, Claims claims) {
        var builder = request.mutate();
        String subject = claims.getSubject();
        if (subject != null) {
            builder.header("X-User-Name", subject);
        }
        Object userId = claims.get("user_Id");
        if (userId != null) {
            builder.header("X-User-Id", String.valueOf(userId));
        }
        Object tenantId = claims.get("tenant_Id");
        if (tenantId != null) {
            builder.header("X-Tenant-Id", String.valueOf(tenantId));
        }
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List<?> roles) {
            String rolesHeader = roles.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            builder.header("X-Roles", rolesHeader);
        }
        Object userType = claims.get("user_Type");
        if (userType != null) {
            builder.header("X-User-Type", String.valueOf(userType));
        }
        return builder.build();
    }

    private Claims parseToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory().wrap(UNAUTHORIZED_JSON.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
