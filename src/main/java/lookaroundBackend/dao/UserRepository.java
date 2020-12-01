package lookaroundBackend.dao;

import org.springframework.data.repository.CrudRepository;

import lookaroundBackend.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    
}
