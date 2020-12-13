package lookaroundBackend.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lookaroundBackend.dao.PostRepository;
import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Multimedia;
import lookaroundBackend.entity.Post;
import lookaroundBackend.entity.User;

@Service
class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @Autowired 
    MultimediaService multimediaService;

    @Transactional // 事务性要求
    public Post createPost(User publisher, String textContent,String publishLoction, Set<Byte[]> fileList){
        Post post = new Post();
        postRepository.save(post);
        post.setPublisher(publisher);
        post.setTextContent(textContent);
        post.setPublishTime(LocalDateTime.now());
        post.setPublishLoction(publishLoction);
        post.setMultimediaContent(saveMultimediaContent(post,fileList));
        postRepository.save(post);
        return post;
    }

    // 若id为null，抛出 IllegalArgumentException
    // 若结果为空，抛出 NoSuchElementException
    public Post findPost(Integer id) throws Exception{
        return postRepository.findById(id).get();
    }    

    public Iterable<Post> findAllPost(){
        return postRepository.findAll();
    }

    @Transactional // 事务性要求
    public Comment addComment(Post associatedPost, User publisher, String textContent){
        Comment e = commentService.createComment(publisher, associatedPost, textContent);
        associatedPost.addComment(e);
        return e;
    }

    private Set<Multimedia> saveMultimediaContent(Post associatedPost, Set<Byte[]> fileList) {
        Set<Multimedia> multimediaContent = new HashSet<>();
        for (Byte[] file : fileList) {
            multimediaContent.add(multimediaService.createMultimedia(associatedPost, file));
        }
        return multimediaContent;
    }
}
