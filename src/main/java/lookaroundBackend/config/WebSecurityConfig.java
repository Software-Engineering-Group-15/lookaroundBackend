package lookaroundBackend.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lookaroundBackend.config.security.JsonLoginFailureHandler;
import lookaroundBackend.config.security.JsonLoginFilter;
import lookaroundBackend.config.security.JsonLoginSuccessHandler;
import lookaroundBackend.config.security.JwtAuthenticationFilter;
import lookaroundBackend.config.security.JwtAuthenticationProvider;
import lookaroundBackend.service.UserManageService;
import lookaroundBackend.utils.EnumGrantedAuthority;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserManageService userManageService;
    
    /**
     * 访问限制
     * 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable()
        .authorizeRequests()
            //.antMatchers(HttpMethod.POST, "/user/register").permitAll()
            //.antMatchers("/user").hasRole(EnumGrantedAuthority.USER.getRole())
            .antMatchers(HttpMethod.GET, "/hello").authenticated()
            .anyRequest().permitAll()
            .and()
        .addFilterAt(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAt(jwtAuthenticationFilter(),BasicAuthenticationFilter.class)
        .httpBasic();
    }

    /**
     * 认证管理
     * 
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("ADMIN").password(passwordEncoder().encode("password"))
            .roles(EnumGrantedAuthority.USER.getRole(), EnumGrantedAuthority.ADMIN.getRole());

        auth.authenticationProvider(jwtAuthenticationProvider());

        /**
         * 注册自定义的认证管理：使用userManageService从数据库中得到用户信息，然后用于验证
         */
        // auth.userDetailsService(userManageService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JsonLoginFilter jsonLoginFilter() throws Exception {
        JsonLoginFilter filter = new JsonLoginFilter(authenticationManager());

        // 登录成功: 生成token并返回给前端，保存在后端
        filter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());

        // 登录失败：回复一个401的Response
        filter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());

        //不将认证后的context放入session
        filter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        return filter;
    }

    @Bean
    public Filter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager());
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider();
    }

}
