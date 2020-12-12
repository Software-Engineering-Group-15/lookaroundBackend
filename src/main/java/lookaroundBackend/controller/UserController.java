package lookaroundBackend.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.entity.User;
import lookaroundBackend.service.PostService;
import lookaroundBackend.service.UserService;

@RestController
public class UserController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // 登录
    @RequestMapping(name = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String username = newRequest.get("userName");
            String password = newRequest.get("password");

             //need to discuss
            //User user = userService.login(username, password);
            //end

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    // 注册
    @RequestMapping(name = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String email = newRequest.get("email") + "@pku.edu.cn";
            String username = newRequest.get("userName");
            String password = newRequest.get("password");

             //need to discuss
            //User user = userService.createUser(email, username, password);
            //end

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    // 注册验证（暂时意义不明）
    @RequestMapping(name = "/user/register/validation", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> registerValidation(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String email = newRequest.get("email") + "@pku.edu.cn";
            String code = newRequest.get("verificationCode");

             //need to discuss
            //...
            //end

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    // 查看信息
    @RequestMapping(name = "/user/profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> register(@Pathvariable(name = "id", required = true) Integer id) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{

             //need to discuss
            User user = userService.findUser(id);
            //end

            profile.put("userName", user.getUserName());
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }

    // 修改个人信息
    @RequestMapping(name = "/user/profile", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> getProfile(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            Integer id = newRequest.get("userID");
            String username = newRequest.get("username");

            //need to discuss
            User user = userService.changeUser(id, username);
            //end

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
        }
        finally{
            return response;
        }
    }
}