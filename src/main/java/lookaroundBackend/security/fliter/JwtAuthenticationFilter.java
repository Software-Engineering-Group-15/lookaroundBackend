package lookaroundBackend.security.fliter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lookaroundBackend.security.JwtToken.JwtTokenUtil;


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
        try{
            // Header中没有验证或验证类型不是Jwt的，放给后面的Filter处理
            String jwtTokenString = JwtTokenUtil.getJwtToken(request);
            if (jwtTokenString == null){
                this.logger.trace("Did not process authentication request since failed to find "
                + "JWT in Bearer Authorization header");
                chain.doFilter(request, response);
                return;
            }

            Authentication authRequest = JwtTokenUtil.getJwtAuthenticationToken(jwtTokenString);
            
            Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
            }

            onSuccessfulAuthentication(request, response, authResult);
            chain.doFilter(request, response);
        }
		catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            
            this.logger.trace("Failed to process authentication request", ex);
            
			onUnsuccessfulAuthentication(request, response, ex);

			return;
		}
        chain.doFilter(request, response);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);
        try {
            request.getRequestDispatcher("/error").forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
