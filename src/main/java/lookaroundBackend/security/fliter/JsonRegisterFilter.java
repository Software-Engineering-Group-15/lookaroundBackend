package lookaroundBackend.security.fliter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lookaroundBackend.JsonBean.UserRegisterBean;
import lookaroundBackend.service.UserManageService;

public class JsonRegisterFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserManageService userManageService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public JsonRegisterFilter(AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher("/register","POST"), authenticationManager);
    }
    
    /**
     * 实现JSON注册. 格式：UserRegisterBean
    */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null){
            return null;
        }
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UserRegisterBean user = mapper.readValue(request.getInputStream(), UserRegisterBean.class);
            String username = user.getUsername();
            String password = user.getPassword(); //未加密
            
            // 保存到数据库
            if(userManageService.registerAsUser(username, passwordEncoder.encode(password)) == null){
                return null;
            }
            
            UsernamePasswordAuthenticationToken authRequest = 
                new UsernamePasswordAuthenticationToken(username, password);

            return this.getAuthenticationManager().authenticate(authRequest);        
        }
        return null;
    }
    
}
