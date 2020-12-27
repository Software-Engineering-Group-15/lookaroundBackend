package lookaroundBackend.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lookaroundBackend.dao.UserRepository;
import lookaroundBackend.entity.User;
import lookaroundBackend.utils.EnumGrantedAuthority;


/**
 * 用户管理服务层.提供注册,验证功能
 */

@Service
@Transactional // 事务性要求
public class UserManageService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    public User findByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Username \"" + username + "\" Not Found");
        }
        return user;    
    } 


    /**
     * 注册。若用户已存在，返回null；否则注册并返回用户
     * @param username
     * @param password
     * @return
     */
    public User registerAsUser(String username, String password){
        User user = null;
        try{
            user = this.findByUsername(username);
        }
        catch (UsernameNotFoundException e){
        }
        if (user == null){
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(EnumGrantedAuthority.USER);
            user = new User(username, password, authorities);
            userRepository.save(user);
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) findByUsername(username);
    }

}
