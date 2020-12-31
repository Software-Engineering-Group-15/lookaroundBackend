package lookaroundBackend.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.JsonBean.MessageBean;

@RestController
public class ErrorController {
    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public MessageBean myError(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString());
    }
}
