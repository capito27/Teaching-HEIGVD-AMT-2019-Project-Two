package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.StadiumsApi;
import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.repositories.StadiumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class StadiumsApiController implements StadiumsApi {

    @Autowired
    StadiumsRepository stadiumsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<List<Stadium>> getStadiumsByUser() {
        List<Stadium> stadiums = stadiumsRepository.findAllByUserId((Integer) httpServletRequest.getAttribute("user_id")).parallelStream()
                .map(StadiumsApiController::toStadium).collect(Collectors.toList());
        return ResponseEntity.ok(stadiums);
    }

    private static Stadium toStadium(StadiumEntity stadiumEntity) {
        Stadium result = new Stadium();
        result.setId(stadiumEntity.getId());
        result.setLocation(stadiumEntity.getLocation());
        result.setName(stadiumEntity.getName());
        result.setNumberOfPlaces(stadiumEntity.getNumberOfPlaces());
        return result;
    }
}
