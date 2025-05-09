package de.dental_clinic.g_43_praxis.security.sec_filter;

import de.dental_clinic.g_43_praxis.security.AuthInfo;
import de.dental_clinic.g_43_praxis.security.sec_service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@AllArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private TokenService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI(); // Используем полный URI вместо ServletPath
//        System.out.println("Request URI: " + requestURI);

        if (requestURI.equals("/api/login") ||
                requestURI.equals("/api/services/active") ||
                requestURI.equals("/api/doctors/active") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.equals("/api/settings") ||
                requestURI.equals("/api/appointment")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Token is missing.");
            return;
        }

        if (!service.validateAccessToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid or expired token.");
            return;
        }

        Claims claims = service.getAccessClaims(token);
        AuthInfo authInfo = service.mapClaimsToAuthInfo(claims);
        authInfo.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authInfo);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}