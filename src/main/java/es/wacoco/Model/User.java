package es.wacoco.Model;
import lombok.Getter;
import lombok.Setter;@Getter
@Setter
public class User {
    private String userName;
    private String userEmail;
    // Getter and Setter for password
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}