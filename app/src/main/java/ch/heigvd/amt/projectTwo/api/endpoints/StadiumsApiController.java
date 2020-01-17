package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.StadiumsApi;
import ch.heigvd.amt.projectTwo.api.exceptions.ForbiddenException;
import ch.heigvd.amt.projectTwo.api.exceptions.NotFoundException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<Stadium> getStadiumById(Integer stadiumId) throws NotFoundException {
        StadiumEntity stadiumEntity = stadiumsRepository.findById(stadiumId).orElseThrow(() -> new NotFoundException(404, "The stadium ID : " + stadiumId + " doesn't exist on the database."));
        Stadium stadium = toStadium(stadiumEntity);
        return ResponseEntity.ok().body(stadium);
    }

    @Override
    public ResponseEntity<StadiumDetails> addStadium(@Valid Stadium stadium) throws ForbiddenException {
        if (!(Boolean) httpServletRequest.getAttribute("user_admin")) {
            throw new ForbiddenException("You are not an administrator");
        }
        StadiumEntity newStadium = toStadiumEntity(stadium);
        stadiumsRepository.save(newStadium);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newStadium.getId()).toUri();

        return ResponseEntity.created(location).body(toStadiumDetails(newStadium));
        //return ResponseEntity.status(HttpStatus.OK).body(toStadiumDetails(newStadium));
    }

    @Override
    public ResponseEntity<Void> deleteStadium(Integer stadiumId) throws NotFoundException, ForbiddenException {
        if (!(Boolean) httpServletRequest.getAttribute("user_admin")) {
            throw new ForbiddenException("You are not an administrator");
        }
        StadiumEntity stadiumToDelete = stadiumsRepository.findById(stadiumId).orElseThrow(() -> new NotFoundException(404, "The stadium ID : " + stadiumId + " doesn't exist on the database."));
        stadiumsRepository.deleteById(stadiumId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateStadium(Integer stadiumId, @Valid Stadium stadium) throws NotFoundException, ForbiddenException {
        if (!(Boolean) httpServletRequest.getAttribute("user_admin")) {
            throw new ForbiddenException("You are not an administrator");
        }
        StadiumEntity stadiumInDB = stadiumsRepository.findById(stadiumId).orElseThrow(() -> new NotFoundException(404, "The stadium ID : " + stadiumId + " doesn't exist on the database."));
        stadiumInDB.setLocation(stadium.getLocation());
        stadiumInDB.setName(stadium.getName());
        stadiumInDB.setNumberOfPlaces(stadium.getNumberOfPlaces());
        stadiumsRepository.save(stadiumInDB);
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
