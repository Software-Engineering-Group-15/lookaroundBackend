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

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public User createUser(String userName, String password) {
        User newUser = new User(userName, password);
        userRepository.save(newUser);
        return newUser;
    }

    public User registerAsUser(String username, String password){
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(EnumGrantedAuthority.USER);
        User newUser = new User(username, password, authorities);
        userRepository.save(newUser);
        return newUser;
    }

    public User registerAsAdmin(String username, String password){
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(EnumGrantedAuthority.USER);
        authorities.add(EnumGrantedAuthority.ADMIN);
        User newUser = new User(username, password, authorities);
        userRepository.save(newUser);
        return newUser;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Username \"" + username + "\" Not Found");
        }
        return user;
    }

}
