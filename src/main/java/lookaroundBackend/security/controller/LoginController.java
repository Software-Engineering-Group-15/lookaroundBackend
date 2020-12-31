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

    @RequestMapping(value = "/login/success", method = RequestMethod.POST)
    public Object LoginSuccess(){
        return new MessageBean(HttpStatus.OK.value(),"Login Success");
    }

    @RequestMapping(value = "/login/error/badCredentials", method = RequestMethod.POST)
    public Object LoginFail(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误: " + e.toString());
    }

    @RequestMapping(value = "/login/error/formatError", method = RequestMethod.POST)
    public Object LoginFail2(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString());
    }
}
