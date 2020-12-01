package lookaroundBackend.dao;

import org.springframework.data.repository.CrudRepository;

import lookaroundBackend.entity.Multimedia;

public interface MultimediaRepository extends CrudRepository<Multimedia, Integer>{
    
}
