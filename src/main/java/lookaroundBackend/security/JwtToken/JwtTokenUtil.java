package lookaroundBackend.security.JwtToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lookaroundBackend.utils.EnumGrantedAuthority;

public class JwtTokenUtil{

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private static final long TOKEN_EXPIRED_TIME = 24 * 60 * 60 * 1000;

    private static final String AUTHENTICATION_SCHEME_JWT = "Bearer";

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
    public static String getJwtToken(HttpServletRequest request) throws AuthenticationException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null) {
			return null;
		}
		header = header.trim();
		if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_JWT)) {
			return null;
		}
		if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT)) {
            throw new BadCredentialsException("Empty JWT authentication token");
            // return null;
		}
        return header.replace("Bearer ", "");
    }


    public static JwtAuthenticationToken getJwtAuthenticationToken(String jwtTokenString){
        if(jwtTokenString == null){
            return null;
        }
        return new JwtAuthenticationToken(null, jwtTokenString);
    }

    /**
     * 验证JwtAuthenticationToken
     * 
     * @param token 未验证的JwtAuthenticationToken
     * @return 已验证的JwtAuthenticationToken
     * @throws UnsupportedJwtException
     */

    public static JwtAuthenticationToken parseJwtToken(JwtAuthenticationToken authToken) throws AuthenticationException {

        String token = authToken.getJwtTokenString();
        String username = "";
        List<String> roles = new ArrayList<String>();
        try {
            Claims body = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
            
            username = body.get("username", String.class);
            roles = body.get("roles", List.class);
            // System.out.println("username: " + username + " roles :" + roles);
            
        } catch (ExpiredJwtException e) { // 可正确解读，凭证已过期
            throw new CredentialsExpiredException("Invalid JWT authentication token");
        } catch (JwtException e){ // 其他错误
            throw new BadCredentialsException("Invalid JWT authentication token");
        } 

        if(username == null) return null;

        return new JwtAuthenticationToken(username, token, EnumGrantedAuthority.rolesToAuthorities(roles));
    }

    public static Authentication convert(HttpServletRequest request) {

        return getJwtAuthenticationToken(getJwtToken(request));
    }

}