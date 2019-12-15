package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.StadiumsApi;
import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.repositories.StadiumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StadiumsApiController implements StadiumsApi {

    @Autowired
    StadiumsRepository stadiumsRepository;

    @Override
    public ResponseEntity<List<Stadium>> getStadiumsByUser() {
        List<Stadium> stadiums = new ArrayList<>();
        // TODO: find by users not all
        for (StadiumEntity stadiumEntity : stadiumsRepository.findAll()) {
            stadiums.add(toStadium(stadiumEntity));
        }
        return ResponseEntity.ok(stadiums);
    }

    private Stadium toStadium(StadiumEntity stadiumEntity) {
        Stadium result = new Stadium();
        result.setId(stadiumEntity.getId());
        result.setLocation(stadiumEntity.getLocation());
        result.setName(stadiumEntity.getName());
        result.setNumberPlaces(stadiumEntity.getNumberOfPlaces());
        return result;
    }
}
