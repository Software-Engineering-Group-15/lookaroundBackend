package lookaroundBackend;

import java.util.List;

import org.junit.jupiter.api.Test;
import lookaroundBackend.utils.EnumGrantedAuthority;


//@SpringBootTest
public class EnumGrantedAuthorityTest {
    @Test
    public void rolesToAuthoritiesTest() {
        List<String> roles = List.of("USER", "ADMID");
        var authorities = EnumGrantedAuthority.rolesToAuthorities(roles);
        System.out.println(authorities);
    }
}
