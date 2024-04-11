package es.wacoco.Model;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class User {
    private String username;
    private String email;
    // Getters and setters

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}