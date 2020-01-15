package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

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

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "team1", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<MatchEntity> matches1 = new HashSet<>();

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy ="team2",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<MatchEntity> matches2 = new HashSet<>();

    // Returns immutable set containing all matches
    public Set<MatchEntity> matches(){
        return Sets.union(matches1, matches2);
    }
}
