package ch.heigvd.amt.projectTwo.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "amt_app.match")
public class MatchEntity implements Serializable {
    @Id
    @Column(name = "id_match")
    private int Id;
    private int score1;
    private int score2;
    @Column(name = "FK_user")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_team1")
    private TeamEntity team1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_team2")
    private TeamEntity team2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_stadium")
    private TeamEntity location;
}
