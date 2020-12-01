package lookaroundBackend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lookaroundBackend.entity.Comment;
import lookaroundBackend.entity.Post;
import lookaroundBackend.entity.User;
import lookaroundBackend.dao.CommentRepository;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    // 要求：User、Post都经过持久化(save)
    public Comment createComment(User publisher, Post associatedPost, String textContent){
        Comment newComment = new Comment();
        newComment.setPublisher(publisher);
        newComment.setAssociatedPost(associatedPost);
        newComment.setPublishTime(LocalDateTime.now());
        newComment.setTextContent(textContent);
        return commentRepository.save(newComment);
    }

    public Comment findComment(Integer id){
        return commentRepository.findById(id).get();
    }
}
