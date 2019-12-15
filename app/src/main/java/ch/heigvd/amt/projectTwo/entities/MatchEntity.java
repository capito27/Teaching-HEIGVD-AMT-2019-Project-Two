package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;

//TODO: Add lombok
@Entity
public class MatchEntity implements Serializable {
    @Id
    private int Id;

}
