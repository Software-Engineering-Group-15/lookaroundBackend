package lookaroundBackend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lookaroundBackend.entity.baseClass.UserCreatedContent;

@Entity
public class Comment extends UserCreatedContent{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Post associatedPost;

    private String textContent;

    public Comment(){}

    @Override
    public String toString() {
        return "Comment [associatedPostId=" + associatedPost.getId() +  ", publisherId="
        + getPublisher().getId()+ ", textContent=" + textContent + "]";
    }

    public Post getAssociatedPost() {
        return associatedPost;
    }

    public void setAssociatedPost(Post associatedPost) {
        this.associatedPost = associatedPost;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


}
