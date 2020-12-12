package lookaroundBackend.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**用户类，内存中的数据对象 
*  通过JPA的注解，实现了与数据库中存储表项的映射
*  实现了UserDetails接口，用于登录和验证
*/
@Entity
public class User implements UserDetails{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5422287720761252791L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    // 用户登录的密码。如何加密？
    private String password;

    // 用于用户验证的"主键" 
	private String username;

    // 用户权限列表
    @Convert(converter = AuthoritiesConverter.class)
	private Set<GrantedAuthority> authorities;

    // 帐户是否已过期
	private boolean accountNonExpired;

    // 账户是否被锁定
	private boolean accountNonLocked;

    // 凭证是否过期
	private boolean credentialsNonExpired;

    // 账户是否可用
	private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Post.class, mappedBy = "publisher")
    private Set<Post> publishPostList;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Comment.class, mappedBy = "publisher")
    private Set<Comment> publishCommentList;

    public User(String username, String password,  Set<? extends GrantedAuthority> authorities){
		this.username = username;
		this.password = password;
		this.enabled = true;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.accountNonLocked = true;
        this.authorities = Collections.unmodifiableSet(authorities);        
        
        this.nickname = username;
        this.publishPostList = new HashSet<Post>();
        this.publishCommentList = new HashSet<Comment>();
    }

    public User(String username, String password) {
        this(username, password, new HashSet<GrantedAuthority>());
    }

    public User(){
        this("User", "password");
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + getUsername() + ", nickname=" + nickname+ ", publishCommentList=" + publishCommentList
                + ", publishPostList=" + publishPostList + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	public boolean addPost(Post newPost) {
        return this.publishPostList.add(newPost);
	}

	public boolean addComment(Comment newComment) {
        return this.publishCommentList.add(newComment);
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Post> getPublishPostList() {
        return publishPostList;
    }

    public void setPublishPostList(Set<Post> publishPostList) {
        this.publishPostList = publishPostList;
    }

    public Set<Comment> getPublishCommentList() {
        return publishCommentList;
    }

    public void setPublishCommentList(Set<Comment> publishCommentList) {
        this.publishCommentList = publishCommentList;
    }
}

/**
 * 进行权限列表和数据库中存储数据的转换
 */
class AuthoritiesConverter implements AttributeConverter<Set<GrantedAuthority>, String>{

    @Override
    public String convertToDatabaseColumn(Set<GrantedAuthority> attribute) {
        StringBuilder sb = new StringBuilder();
        for(GrantedAuthority authority: attribute){
            sb.append(authority.toString()).append(',');
        }
        return sb.toString();
    }

    @Override
    public Set<GrantedAuthority> convertToEntityAttribute(String dbData) {
        String[] authorities = dbData.split(",");
        Set<GrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        for (String role : authorities) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}