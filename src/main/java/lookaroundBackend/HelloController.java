package lookaroundBackend;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello,world! " + "User: "+ SecurityContextHolder.getContext().getAuthentication().toString();
    }
}
