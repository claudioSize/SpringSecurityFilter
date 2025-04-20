package fav.com.springsecurityfirsttime1.Entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOUser {
    String username;
    String password;
    String rol;
}
