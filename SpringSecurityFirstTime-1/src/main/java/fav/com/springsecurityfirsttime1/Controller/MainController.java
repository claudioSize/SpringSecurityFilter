package fav.com.springsecurityfirsttime1.Controller;


import fav.com.springsecurityfirsttime1.Entitys.DTOClaim;
import fav.com.springsecurityfirsttime1.Entitys.DTOUser;
import fav.com.springsecurityfirsttime1.Service.valdation;
import fav.com.springsecurityfirsttime1.Service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MainController {
    @Autowired
    ServiceUser newUser;
    
    @GetMapping("/hola/")
    public String getHola(){
        return "hola";
    }
    @PostMapping("/login/")
    public ResponseEntity<String> loginUsers(@RequestBody DTOUser user){

        return ResponseEntity.ok(newUser.loginUser(user));
    }
    @GetMapping("/token/")
    public ResponseEntity<String> funko(Authentication authentication){
        authentication.isAuthenticated();
        return ResponseEntity.ok("holaaaaa");
    }
    @GetMapping("/holaanimo/")
    public ResponseEntity<String> hola(Authentication authentication){

        return ResponseEntity.ok(authentication.getName());
    }
    @PostMapping("/register/")
    public ResponseEntity<HttpStatus> createUser(@RequestBody DTOUser user){
        newUser.createUser(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
