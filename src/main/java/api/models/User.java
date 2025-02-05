package api.models;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private String username;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = null;
    }
}
