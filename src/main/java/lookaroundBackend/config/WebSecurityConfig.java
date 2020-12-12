package lookaroundBackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lookaroundBackend.service.UserManageService;
import lookaroundBackend.utils.EnumGrantedAuthority;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserManageService userManageService;

    /** 访问限制
     * 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/", "/login", "/error").permitAll() // 是不是应该在utils里构造一下常量，不用神秘字符串
            .antMatchers("/user").hasRole(EnumGrantedAuthority.USER.getRole())
        .and()
            .formLogin();
    }

    /** 认证管理
     * 
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * 注册自定义的认证管理：使用userManageService从数据库中得到用户信息，然后用于验证
         */
        auth.userDetailsService(userManageService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
