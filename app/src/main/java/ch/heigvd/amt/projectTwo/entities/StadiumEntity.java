package ch.heigvd.amt.projectTwo.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name= "stadium")
public class StadiumEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stadium")
    private Integer Id;
    private String location;
    private String name;
    @Column(name = "places")
    private int numberOfPlaces;

    /*@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchEntity> matches = new ArrayList<>();*/
}
