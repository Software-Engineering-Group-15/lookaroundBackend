package lookaroundBackend.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userName;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Post.class, mappedBy = "publisher")
    private Set<Post> publishPostList = Set.of();

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class, mappedBy = "publisher")
    private Set<Comment> publishCommentList = Set.of();

    public User(){}

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", publishCommentList=" + publishCommentList + ", publishPostList=" + publishPostList
                + "]";
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}