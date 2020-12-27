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


    // TODO: 这部分要完全转交给spring security做，用Filter实现

    // 登录
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> newRequest) {

        return getResonse(300, new HashMap<String,Object>());
    }

    // 注册
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody Map<String,Object> newRequest) {
        Map<String,Object> response = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<String,Object>(); 
        Map<String,Object> profile = new HashMap<String,Object>();
        try{
            String email = newRequest.get("email").toString() + "@pku.edu.cn";
            String username = newRequest.get("userName").toString();
            String password = newRequest.get("password").toString();

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
            String email = newRequest.get("email").toString() + "@pku.edu.cn";
            String code = newRequest.get("verificationCode").toString();

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
    @RequestMapping(value = "/user/profile/{username}", method = RequestMethod.GET)
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
            Integer id = Integer.parseInt(newRequest.get("userID").toString());
            String username = newRequest.get("username").toString();

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