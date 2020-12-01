package lookaroundBackend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lookaroundBackend.dao.UserRepository;
import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Post;
import lookaroundBackend.entity.User;

@Service
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
        return userRepository.save(newUser);
    }

    public User findUser(Integer id){
        return userRepository.findById(id).get();
    }

    public Post publishPost(Integer publisherId, String textContent, String publishLoction, Set<Byte[]> fileList){
        User publisher = findUser(publisherId);
        Post newPost = postService.createPost(publisher, textContent,publishLoction, fileList);
        publisher.getPublishPostList().add(newPost);
        return newPost;
    }

    public Comment publishComment(Integer publisherId, Integer postId, String textContent){
        User publisher = findUser(publisherId);
        Post associatedPost = postService.findPost(postId);
        Comment newComment = postService.addComment(associatedPost, publisher, textContent);
        publisher.getPublishCommentList().add(newComment);
        return newComment;
    }

}
