package lookaroundBackend.dao;

import org.springframework.data.repository.CrudRepository;

import lookaroundBackend.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
    
}
