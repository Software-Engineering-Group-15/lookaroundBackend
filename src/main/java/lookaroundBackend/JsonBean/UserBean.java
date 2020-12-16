package lookaroundBackend.JsonBean;

/**
* 用于登录的用户信息JSON文件格式
*/
public class UserBean {
   private String username;
   private String password;

   public UserBean(){}

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }
}
