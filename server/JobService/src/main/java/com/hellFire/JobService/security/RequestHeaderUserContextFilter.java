package com.hellFire.JobService.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class RequestHeaderUserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String username = request.getHeader("X-User-Name");
        String rolesHeader = request.getHeader("X-Roles");
        String tenantIdHeader = request.getHeader("X-Tenant-Id");
        String userIdHeader = request.getHeader("X-User-Id");

        UserContext context = new UserContext();

        if (username != null)
            context.setUsername(username);

        if (rolesHeader != null)
            context.setRoles(Arrays.asList(rolesHeader.split(",")));

        if (tenantIdHeader != null)
            context.setTenantId(Long.parseLong(tenantIdHeader));

        if (userIdHeader != null)
            context.setUserId(Long.parseLong(userIdHeader));

        UserContextHolder.set(context);

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }
}
