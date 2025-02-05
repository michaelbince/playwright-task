package api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUser {
    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;
}
