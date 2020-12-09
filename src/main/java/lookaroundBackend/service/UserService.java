package lookaroundBackend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lookaroundBackend.dao.UserRepository;
import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Post;
import lookaroundBackend.entity.User;

@Service
@Transactional // 事务性要求
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired 
    CommentService commentService;
 
    public User createUser(String userName){ 
        User newUser = new User();
        newUser.setUserName(userName);
        userRepository.save(newUser);
        return newUser;
    }

    public User findUser(Integer id){
        return userRepository.findById(id).get();
    }

    public Post publishPost(User publisher, String textContent, String publishLoction, Set<Byte[]> fileList){
        Post newPost = postService.createPost(publisher, textContent,publishLoction, fileList);
        publisher.addPost(newPost);
        userRepository.save(publisher);
        return newPost;
    }

    public Comment publishComment(User publisher, Post associatedPost, String textContent){
        Comment newComment = postService.addComment(associatedPost, publisher, textContent);
        publisher.addComment(newComment);
        userRepository.save(publisher);
        return newComment;
    }

}
