package lookaroundBackend.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime publishTime; // 日期格式为：年月日时分秒纳秒（毫微秒）

    private String publishLoction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User publisher;

    private String textContent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "associatedPost")
    private Set<Multimedia> multimediaContent = Set.of();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "associatedPost")
    private Set<Comment> commentList = Set.of();

    public Post(){}

    public Post(String textContent) {
        this.publishTime = LocalDateTime.now();
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Post [commentList=" + commentList + ", id=" + id + ", multimediaContent=" + multimediaContent
                + ", publishLoction=" + publishLoction + ", publishTime=" + publishTime + ", publisherId=" + publisher.getId()
                + ", textContent=" + textContent + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Set<Multimedia> getMultimediaContent() {
        return multimediaContent;
    }

    public void setMultimediaContent(Set<Multimedia> multimediaContent) {
        this.multimediaContent = multimediaContent;
    }

    public Set<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(Set<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getPublishLoction() {
        return publishLoction;
    }

    public void setPublishLoction(String publishLoction) {
        this.publishLoction = publishLoction;
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
        Post other = (Post) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
