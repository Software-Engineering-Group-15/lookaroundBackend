package lookaroundBackend.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lookaroundBackend.dao.UserRepository;
import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Post;
import lookaroundBackend.entity.User;

/**
 * 用户发布评论、动态的服务接口
 */
@Service
@Transactional
public class PublishService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public Post publishPost(User publisher, String textContent, String publishLoction, Set<Byte[]> fileList) {
        Post newPost = postService.createPost(publisher, textContent, publishLoction, fileList);
        publisher.addPost(newPost);
        userRepository.save(publisher);
        return newPost;
    }

    public Comment publishComment(User publisher, Post associatedPost, String textContent) {
        Comment newComment = postService.addComment(associatedPost, publisher, textContent);
        publisher.addComment(newComment);
        userRepository.save(publisher);
        return newComment;
    }
}
