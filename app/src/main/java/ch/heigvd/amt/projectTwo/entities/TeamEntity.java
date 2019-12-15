package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

//TODO: Add lombok
@Entity
public class TeamEntity implements Serializable {
    @Id
    private int Id;
    private String name;
    private String country;

    public BigDecimal getId() {
        return BigDecimal.valueOf(Id);
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
