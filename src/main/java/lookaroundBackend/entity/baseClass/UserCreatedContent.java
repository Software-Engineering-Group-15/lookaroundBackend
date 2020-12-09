/*
    用户创建内容类,对Post和Comment类中创建者和创建时间的抽象
*/

package lookaroundBackend.entity.baseClass;

import java.time.LocalDateTime;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lookaroundBackend.entity.User;

@MappedSuperclass
public class UserCreatedContent {
    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    // 创建者
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User publisher;

    // 创建时间
    private LocalDateTime publishTime; // 日期格式为：年月日时分秒纳秒（毫微秒）

    public UserCreatedContent(){}

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
        UserCreatedContent other = (UserCreatedContent) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
    
}
