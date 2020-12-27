package lookaroundBackend.security.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lookaroundBackend.utils.EnumGrantedAuthority;

public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final long TOKEN_EXPIRED_TIME = 24 * 60 * 60 * 1000;
    private static String SECRET_KEY = String.format("Lanius42");

    public static String createJwtToken(Authentication authResult) {
        long now = System.currentTimeMillis();
        long exp = now + TOKEN_EXPIRED_TIME;

        String username = authResult.getName();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        List<String> roles = new ArrayList<String>();
        if (authorities != null){
            for (GrantedAuthority authority : authorities){
                roles.add(authority.getAuthority());
            }
        }

        return Jwts.builder()
                .claim("username", username)
                .claim("roles", roles)
                .setIssuedAt(new Date(now)) // iat: jwt的签署时间
                .setExpiration(new Date(exp)) // exp: jwt的过期时间
                .signWith(ALGORITHM, SECRET_KEY)
                .compact();
    }

    /**
     * 从HttpServletRequest中解析出Token字符串
     * 
     * @param request
     * @return JwtToken字符串
     */
    public static String getJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null)
            return null;
        return header.replace("Bearer ", "");
    }


    public static JwtAuthenticationToken getJwtAuthenticationToken(String jwtTokenString){
        return new JwtAuthenticationToken(null, jwtTokenString);
    }

    /**
     * 验证JwtAuthenticationToken
     * 
     * @param token 未验证的JwtAuthenticationToken
     * @return 已验证的JwtAuthenticationToken
     * @throws UnsupportedJwtException
     */

    public static JwtAuthenticationToken parseJwtToken(JwtAuthenticationToken authToken) throws UnsupportedJwtException {


        String token = authToken.getJwtTokenString();
        String username = null;
        List<String> roles = null;
        try {
            Claims body = Jwts
                .parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            
            username = body.get("username", String.class);
            roles = body.get("roles", List.class);
            // System.out.println("username: " + username + " roles :" + roles);
            
        } catch (Exception e) {

            // throw new UnsupportedJwtException(e.toString() + " 校验的JwtToken有问题: " + token);
        }
        if(username == null) return null;

        return new JwtAuthenticationToken(username, token, EnumGrantedAuthority.rolesToAuthorities(roles));
    }

}