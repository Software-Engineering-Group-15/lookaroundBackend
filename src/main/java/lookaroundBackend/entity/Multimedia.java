package lookaroundBackend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Multimedia {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String url;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post associatedPost;

    public Multimedia(){}

    public Integer getId(){
        return id;
    }

    public String getUrl(){
        return url;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Post getAssociatedPost() {
        return associatedPost;
    }

    public void setAssociatedPost(Post associatedPost) {
        this.associatedPost = associatedPost;
    }

    @Override
    public String toString() {
        return "Multimedia [associatedPostId=" + associatedPost.getId() + ", id=" + id + ", url=" + url + "]";
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
        Multimedia other = (Multimedia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
