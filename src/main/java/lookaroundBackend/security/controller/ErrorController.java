package lookaroundBackend.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.JsonBean.MessageBean;

@RestController
@RequestMapping(value = "/error")
public class ErrorController {
    @RequestMapping(value = "/authenticationFail", method = {RequestMethod.GET, RequestMethod.POST})
    public MessageBean myError(@RequestAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) RuntimeException e){
        return new MessageBean(HttpStatus.UNAUTHORIZED.value(), e.toString());
    }

    @RequestMapping(value = "/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
    public MessageBean myError2(@RequestAttribute( WebAttributes.ACCESS_DENIED_403) RuntimeException e){
        return new MessageBean(HttpStatus.FORBIDDEN.value(), e.toString());
    }
}
