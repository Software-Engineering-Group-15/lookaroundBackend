
/*
测试数据库部分的代码
*/

package lookaroundBackend;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lookaroundBackend.service.UserService;



@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Autowired
    private UserService UserService;
    
    @Bean
    CommandLineRunner initDatabase() {
      return args -> {
          var user1 = UserService.createUser("Alice");
          var user2 = UserService.createUser("Bob");

          Byte[] f1 = new Byte[] {Byte.valueOf("1"), Byte.valueOf("2")};
          Byte[] f2 = new Byte[] {Byte.valueOf("3"), Byte.valueOf("4")};
          Byte[] f3 = new Byte[] {Byte.valueOf("5"), Byte.valueOf("6")};
          Set<Byte[]> s1 = new HashSet<>(); s1.add(f1);
          Set<Byte[]> s2 = new HashSet<>(); s2.add(f2); s2.add(f3);

          var p1 = UserService.publishPost(user1.getId(), "Alice' post", "Loc A", s1);
          var p2 = UserService.publishPost(user2.getId(), "Bob' post", "Loc B", s2);
          var c1 = UserService.publishComment(user1.getId(), p1.getId(), "Alice' Comment");
          var c2 = UserService.publishComment(user2.getId(), p2.getId(), "Bob' Comment");

          log.info("User1: " + user1);
          log.info("User2: " + user2);
          log.info("Post1: " + p1);
          log.info("Post2: " + p2);
          log.info("Comment1: " + c1);
          log.info("Comment2: " + c2);
      };
    }
}
