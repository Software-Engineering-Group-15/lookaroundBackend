package lookaroundBackend.security;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lookaroundBackend.security.fliter.JsonLoginFilter;
import lookaroundBackend.security.fliter.JsonRegisterFilter;
import lookaroundBackend.security.fliter.JwtAuthenticationFilter;
import lookaroundBackend.security.fliter.handle.JsonLoginFailureHandler;
import lookaroundBackend.security.fliter.handle.JsonLoginSuccessHandler;
import lookaroundBackend.security.provider.JwtAuthenticationProvider;
import lookaroundBackend.service.UserManageService;
import lookaroundBackend.utils.EnumGrantedAuthority;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserManageService userManageService;
    
    /**
     * 访问限制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable()
        .authorizeRequests()
            //.antMatchers(HttpMethod.POST, "/user/register").permitAll()
            //.antMatchers("/user").hasRole(EnumGrantedAuthority.USER.getRole())
            .antMatchers(HttpMethod.GET, "/hello").hasRole("USER")
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

        
        auth.authenticationProvider(jwtAuthenticationProvider());

        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("ADMIN").password(passwordEncoder().encode("password"))
            .roles(EnumGrantedAuthority.USER.getRole(), EnumGrantedAuthority.ADMIN.getRole());

        /**
         * 注册自定义的认证管理：使用userManageService从数据库中得到用户信息，然后用于验证
         */
        auth.userDetailsService(userManageService)
        .passwordEncoder(passwordEncoder());
    }

    /**
     * 配置跨域访问
     */
    @Bean 
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    } 

    @Bean
    public JsonLoginFilter jsonLoginFilter() throws Exception {
        JsonLoginFilter filter = new JsonLoginFilter(authenticationManager());

        // 登录成功: 生成token并返回给前端
        filter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());

        // 登录失败：回复一个401的Response
        filter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());

        //不将认证后的context放入session
        filter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        return filter;
    }

    @Bean 
    public JsonRegisterFilter jsonRegisterFilter() throws Exception{
        JsonRegisterFilter filter = new JsonRegisterFilter(authenticationManager());

        // 注册成功: 登录成功的处理
        filter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());

        // 登录失败：登录失败的处理
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
