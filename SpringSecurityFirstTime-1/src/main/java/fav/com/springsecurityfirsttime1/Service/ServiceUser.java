package fav.com.springsecurityfirsttime1.Service;

import fav.com.springsecurityfirsttime1.Entitys.DTOUser;
import fav.com.springsecurityfirsttime1.Entitys.UserEntity;
import fav.com.springsecurityfirsttime1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {
    @Autowired
    Jwt jwt;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String loginUser(DTOUser user){
        UserEntity userEntity = userRepository.findByUsername(user.getUsername());
        if (passwordEncoder.matches(user.getPassword(), userEntity.getPassword())){
            return jwt.buildToken(userEntity);
        }else {
            throw new RuntimeException("error");
        }
    }
    public void createUser(DTOUser user){
        userRepository.save(UserEntity.builder().username(user.getUsername())
                .rol("USER").password(passwordEncoder.encode(user.getPassword())).build());

    }

}
