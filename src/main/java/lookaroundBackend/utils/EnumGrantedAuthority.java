package lookaroundBackend.utils;

import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义的用户角色枚举类
 * 用两个getter得到角色的string，用于构造GrantedAuthority和Role。
 */
public enum EnumGrantedAuthority implements GrantedAuthority{
    USER("USER"), ADMIN("ADMIN") ;

    private final String role; // 用于路径权限规则匹配
    private final String authority; // 用于自定义权限验证

    private EnumGrantedAuthority(String role){
        this.role = role;
        this.authority = "ROLE_" + role;
    }

    public String getRole(){
        return role;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}