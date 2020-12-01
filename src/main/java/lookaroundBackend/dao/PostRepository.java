package lookaroundBackend.dao;

import org.springframework.data.repository.CrudRepository;

import lookaroundBackend.entity.Post;

// domain class: Post
// ID type: Integer
public interface PostRepository extends CrudRepository<Post, Integer>{
    
}
