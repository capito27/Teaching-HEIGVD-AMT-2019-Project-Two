package ch.heigvd.amt.projectTwo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

//TODO: Add lombok
@Entity
public class StadiumEntity implements Serializable {
    @Id
    private int Id;

    public BigDecimal getId() {
        return BigDecimal.valueOf(Id);
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNumberOfPlaces() {
        return BigDecimal.valueOf(numberOfPlaces);
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    private String location;
    private String name;
    private int numberOfPlaces;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
}
