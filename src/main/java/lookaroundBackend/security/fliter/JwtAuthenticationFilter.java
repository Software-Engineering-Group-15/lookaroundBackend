package lookaroundBackend.security.fliter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import lookaroundBackend.security.token.JwtTokenUtil;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private RequestHeaderRequestMatcher requiresAuthenticationRequestMatcher 
        = new RequestHeaderRequestMatcher("Authorization");


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);

    }


    protected boolean requiresAuthentication(HttpServletRequest request,
        HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Header中没有验证或验证类型不是Jwt的，放给后面的Filter处理
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtTokenString = JwtTokenUtil.getJwtToken(request);

        if (jwtTokenString == null){
            chain.doFilter(request, response);
            return;
        }

        //AuthenticationException failed = null;
        Authentication authRequest = JwtTokenUtil.getJwtAuthenticationToken(jwtTokenString);
        Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        onSuccessfulAuthentication(request, response, authResult);
        chain.doFilter(request, response);
    }

}
