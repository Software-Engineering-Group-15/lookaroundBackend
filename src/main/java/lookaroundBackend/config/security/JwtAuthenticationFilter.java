package lookaroundBackend.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        // Header中没有验证或验证类型不是Jwt的，放给后面的Filter处理
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        AuthenticationException failed = null;
        Authentication authRequest = JwtTokenUtil.parseJwtToken(request);
        Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);

        if (authResult != null) { // token认证成功
            SecurityContextHolder.getContext().setAuthentication(authResult);
            onSuccessfulAuthentication(request, response, authResult);
        } else {
            onUnsuccessfulAuthentication(request, response, failed);
            return;
        }
        chain.doFilter(request, response);
    }

    
}
