package lookaroundBackend.config.security;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

// TODO：实现JwtToken中的UserDetails存储，保存用户的Role
public class JwtTokenUtil {

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final long TOKEN_EXPIRED_TIME = 24 * 60 * 60;
    private static String SECRET_KEY = String.format("Lanius42");

    public static String createJwtToken(UsernamePasswordAuthenticationToken authResult) {
        long now = System.currentTimeMillis();
        long exp = now + TOKEN_EXPIRED_TIME;
        // UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        return Jwts.builder().setSubject((String) authResult.getPrincipal()) // sub:jwt所面向的用户
                .setIssuedAt(new Date(now)).setExpiration(new Date(exp)) // exp: jwt的过期时间
                .signWith(ALGORITHM, SECRET_KEY).compact();
    }

    public static JwtAuthenticationToken parseJwtToken(HttpServletRequest request) throws UnsupportedJwtException {

        String token = request.getHeader("Authorization");
        String username = null;
        try {
            username = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody()
                    .getSubject();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.toString() + " 校验的JwtToken有问题 ");
        }
        if (username != null) {
            return new JwtAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }

}