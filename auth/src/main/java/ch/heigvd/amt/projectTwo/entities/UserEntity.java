package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */

//TODO: Add lombok
@Entity
@Table(name="user")
public class UserEntity implements Serializable {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    private String email;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String firstname;
    private String lastname;
    private String password;

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
