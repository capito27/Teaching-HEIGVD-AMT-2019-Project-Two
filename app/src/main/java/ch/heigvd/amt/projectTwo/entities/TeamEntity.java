package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
@Entity
@Table(name= "team")
public class TeamEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_team")
    private int Id;
    private String name;
    private String country;

    /*@OneToMany(mappedBy = "team1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchEntity> matches1 = new ArrayList<>();

    @OneToMany(mappedBy ="team2",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchEntity> matches2 = new ArrayList<>();*/
}
