package fav.com.springsecurityfirsttime1.Repository;

import fav.com.springsecurityfirsttime1.Entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity ,Integer> {
    UserEntity findByUsername(String username);
}
