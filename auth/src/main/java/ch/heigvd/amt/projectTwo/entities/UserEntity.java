package ch.heigvd.amt.projectTwo.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */

@Entity
@Table(name="user")
@Data
public class UserEntity implements Serializable {
    @Id
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    @Column(name = "id_user")
    @ToString.Exclude
    private Integer id;
    @ToString.Exclude
    private boolean isAdmin;
}
