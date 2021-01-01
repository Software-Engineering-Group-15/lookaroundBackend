package lookaroundBackend.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.JsonBean.MessageBean;

@RestController
public class LoginController {

    // @RequestMapping(value = "/user/login/**", method = RequestMethod.POST)
    // public String loginFoward(){
    //     return "forward:/login";
    // } 


    @RequestMapping(value = "/login/success", method = RequestMethod.POST)
    public Object loginSuccess(){
        return new MessageBean(HttpStatus.OK.value(),"Login Success");
    }

    @RequestMapping(value = "/login/error/badCredentials", method = RequestMethod.POST)
    public Object loginFail(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), "Wrong username or password: " + e.toString());
    }

    @RequestMapping(value = "/login/error/formatError", method = RequestMethod.POST)
    public Object loginFail2(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString());
    }

    @RequestMapping(value = "/register/success", method = RequestMethod.POST)
    public Object registerSuccess(){
        return new MessageBean(HttpStatus.OK.value(), "Register Success");
    }


    @RequestMapping(value = "/register/error/formatError", method = RequestMethod.POST)
    public Object registerFail1(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString());
    }

    @RequestMapping(value = "/register/error/usernameRegistered", method = RequestMethod.POST)
    public Object registerFail2(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString() );
    }

}
