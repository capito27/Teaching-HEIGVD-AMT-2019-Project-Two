package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.StadiumsApi;
import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.api.model.StadiumDetails;
import ch.heigvd.amt.projectTwo.api.model.TeamDetails;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import ch.heigvd.amt.projectTwo.repositories.StadiumsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    Logger logger = LoggerFactory.getLogger(StadiumsApiController.class);

    @Override
    public ResponseEntity<List<StadiumDetails>> getStadiumsByUser() {
        List<StadiumDetails> stadiums = StreamSupport.stream(stadiumsRepository.findAll().spliterator(), true).map(StadiumsApiController::toStadiumDetails).collect(Collectors.toList());
        return ResponseEntity.ok(stadiums);
    }

    @Override
    public ResponseEntity<Stadium> getStadiumById(Integer stadiumId) {
        StadiumEntity stadiumEntity = stadiumsRepository.findById(stadiumId).orElse(null);
        if(stadiumEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(stadiumEntity.getUserId() != (Integer)  httpServletRequest.getAttribute("user_id")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Stadium stadium = toStadium(stadiumEntity);
        return ResponseEntity.ok().body(stadium);
    }

    @Override
    public ResponseEntity<Void> addStadium(@Valid Stadium stadium) {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        StadiumEntity newStadium = toStadiumEntity(stadium);
        //TODO: améliorer erreurs
        newStadium.setUserId((Integer) httpServletRequest.getAttribute("user_id"));
        stadiumsRepository.save(newStadium);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteStadium(Integer stadiumId) {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        StadiumEntity stadiumToDelete = stadiumsRepository.findById(stadiumId).orElse(null);
        if(stadiumToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(stadiumToDelete.getUserId() != (Integer) httpServletRequest.getAttribute("user_id")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        stadiumsRepository.deleteById(stadiumId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateStadium(Integer stadiumId, @Valid Stadium stadium) {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        StadiumEntity newStadium = toStadiumEntity(stadium);
        StadiumEntity stadiumInDB = stadiumsRepository.findById(stadiumId).orElse(null);
        if(stadiumInDB == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //TODO: améliorer erreurs
        newStadium.setUserId((Integer) httpServletRequest.getAttribute("user_id"));
        newStadium.setId(stadiumId);
        stadiumsRepository.save(newStadium);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private static StadiumDetails toStadiumDetails(StadiumEntity stadiumEntity) {
        StadiumDetails result = new StadiumDetails();
        result.setId(stadiumEntity.getId());
        result.setLocation(stadiumEntity.getLocation());
        result.setName(stadiumEntity.getName());
        result.setNumberOfPlaces(stadiumEntity.getNumberOfPlaces());
        return result;
    }

    private static Stadium toStadium(StadiumEntity stadiumEntity) {
        Stadium result = new Stadium();
        result.setLocation(stadiumEntity.getLocation());
        result.setName(stadiumEntity.getName());
        result.setNumberOfPlaces(stadiumEntity.getNumberOfPlaces());
        return result;
    }

    private static StadiumEntity toStadiumEntity(Stadium stadium) {
        StadiumEntity result = new StadiumEntity();
        result.setLocation(stadium.getLocation());
        result.setName(stadium.getName());
        result.setNumberOfPlaces(stadium.getNumberOfPlaces());
        return result;
    }
}
