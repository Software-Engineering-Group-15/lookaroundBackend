package lookaroundBackend.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lookaroundBackend.entity.baseClass.UserCreatedContent;

@Entity
public class Post extends UserCreatedContent {

    // 需要API和前端的支持
    private String publishLoction;

    private String textContent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "associatedPost")
    private Set<Multimedia> multimediaContent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "associatedPost")
    private Set<Comment> commentList;
    
    public Post(){
        this.multimediaContent = new HashSet<Multimedia>();
        this.commentList = new HashSet<Comment>();
    }

    public Post(String textContent) {
        this.setPublishTime(LocalDateTime.now());
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Post [commentList=" + commentList + ", id=" + getId() + ", multimediaContent=" + multimediaContent
                + ", publishLoction=" + publishLoction + ", publishTime=" + getPublishTime() + ", publisherId="
                + getPublisher().getId()
                + ", textContent=" + textContent + "]";
    }

    public String getPublishLoction() {
        return publishLoction;
    }

    public void setPublishLoction(String publishLoction) {
        this.publishLoction = publishLoction;
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

	public boolean addComment(Comment e) {
        return this.commentList.add(e);
	}

}
