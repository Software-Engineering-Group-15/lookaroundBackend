
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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lookaroundBackend.service.PublishService;
import lookaroundBackend.service.UserManageService;


@Configuration
@EntityScan(basePackages = { "lookaroundBackend.entity" })
@EnableJpaRepositories(basePackages = { "lookaroundBackend.dao" })
public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  @Autowired
  private PublishService publishService; 
  @Autowired
  private UserManageService userManageService;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Bean
    CommandLineRunner initDatabase() {
      return args -> {

          userManageService.registerAsAdmin("ADMIN", passwordEncoder.encode("password"));

          var user1 = userManageService.registerAsUser("Alice",passwordEncoder.encode("password"));
          var user2 = userManageService.registerAsUser("Bob",passwordEncoder.encode("password"));

          // Byte[] f1 = new Byte[] {Byte.valueOf("1"), Byte.valueOf("2")};
          // Byte[] f2 = new Byte[] {Byte.valueOf("3"), Byte.valueOf("4")};
          // Byte[] f3 = new Byte[] {Byte.valueOf("5"), Byte.valueOf("6")};
          Set<Byte[]> s1 = new HashSet<>(); 
          // s1.add(f1);
          Set<Byte[]> s2 = new HashSet<>(); 
          // s2.add(f2); s2.add(f3);

          var p1 = publishService.publishPost(user1, "Alice's post", "116.298,39.983", s1);
          var p2 = publishService.publishPost(user2, "Bob's post", "116.311,40.001", s2);
          var c1 = publishService.publishComment(user1, p1, "Alice's Comment");
          var c2 = publishService.publishComment(user2, p2, "Bob's Comment");
          var c3 = publishService.publishComment(user1, p2, "Alice's Comment");
          var c4 = publishService.publishComment(user2, p1, "Bob's Comment");

          log.info("User1: " + user1);
          log.info("User2: " + user2);
          log.info("Post1: " + p1);
          log.info("Post2: " + p2);
          log.info("Comment1: " + c1);
          log.info("Comment2: " + c2);
          log.info("Comment3: " + c3);
          log.info("Comment4: " + c4);
      };
    }
}
