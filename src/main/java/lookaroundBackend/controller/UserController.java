package lookaroundBackend.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lookaroundBackend.entity.User;
import lookaroundBackend.service.UserManageService;

@RestController
public class UserController {

    @Autowired
    private UserManageService userManageService;

    //生成response
    private Map<String,Object> getResonse(Integer code, Map<String,Object> data){
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("code", code);
        response.put("data", data);
        return response;
    }


    // TODO: 这部分要完全转交给spring security做，用转发实现

    // 登录
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> newRequest) {

        /*
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String username = (String)newRequest.get("userName");
            String password = (String)newRequest.get("password");

             //need to discuss
            //User user = userService.login(username, password);
            //end
            User user = new User();

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
        */
        return null;
    }

    // 注册
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String email = newRequest.get("email") + "@pku.edu.cn";
            String username = (String)newRequest.get("userName");
            String password = (String)newRequest.get("password");

            //need to discuss
            //User user = userService.createUser(email, username, password);
            //end

            // User user = new User();
            User user = userManageService.registerAsUser(username, password);

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
            return response;

        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;

        }

    }

    // TODO: 确实意义不明
    // 注册验证（暂时意义不明）
    @RequestMapping(value = "/user/register/validation", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> registerValidation(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String email = newRequest.get("email") + "@pku.edu.cn";
            String code = (String)newRequest.get("verificationCode");

             //need to discuss
            //...
            //end
            User user = new User();

            profile.put("userName", user.getUsername());
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
            return response;

        } catch (Exception e) {
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }

    // 查看信息
    @RequestMapping(value = "/user/profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> register(@PathVariable(name = "username", required = true) String username) {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        try {

            // need to discuss
            User user = userManageService.findByUsername(username);
            // end

            profile.put("userName", user.getUsername());
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
             return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }

    }

    // 修改个人信息
    @RequestMapping(value = "/user/profile/change", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> getProfile(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            Integer id = (Integer)newRequest.get("userID");
            String username = (String)newRequest.get("username");

            //need to discuss
            //User user = userService.changeUser(id, username);
            //end
            User user = new User();

            profile.put("userName", username);
            profile.put("userID", user.getId());
            data.put("msg", "success");
            data.put("profile", profile);
            response = getResonse(200, data);
            return response;
        }catch(Exception e){
            data.clear();
            data.put("msg", e.getMessage());
            response = getResonse(300, data);
            return response;
        }
    }
}