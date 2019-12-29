package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.CancellationsApi;
import ch.heigvd.amt.projectTwo.api.exceptions.ForbiddenException;
import ch.heigvd.amt.projectTwo.api.exceptions.NotFoundException;
import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.api.model.MatchDetails;
import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import ch.heigvd.amt.projectTwo.repositories.MatchesRepository;
import ch.heigvd.amt.projectTwo.repositories.StadiumsRepository;
import ch.heigvd.amt.projectTwo.repositories.TeamsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CancellationsApiController implements CancellationsApi {
    @Autowired
    MatchesRepository matchesRepository;
    @Autowired
    TeamsRepository teamsRepository;
    @Autowired
    StadiumsRepository stadiumsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(CancellationsApiController.class);

    @Override
    public ResponseEntity<Void> deleteMatch(@Valid MatchDetails matchDetails) throws NotFoundException, ForbiddenException {
        MatchEntity matchToDelete = toMatchEntity(matchDetails);
        MatchEntity matchInDB = matchesRepository.findById(matchDetails.getId()).orElseThrow(() -> new NotFoundException(404, "The match ID : " + matchToDelete.getId() + " doesn't exist on the database."));
        if(matchInDB.getUserId() != (Integer) httpServletRequest.getAttribute("user_id")) {
            throw new ForbiddenException("This match doesn't belong to you");
        }
        matchesRepository.deleteById(matchToDelete.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private MatchEntity toMatchEntity(MatchDetails match) throws NotFoundException {
        String stadiumId = match.getLocation().split("/")[2];
        logger.info("Value from post req : " + stadiumId);
        logger.info("The match treated : " + match.toString());
        String team1Id = match.getTeam1().split("/")[2];
        String team2Id = match.getTeam2().split("/")[2];
        // TODO parseInt throws exception, catch them
        StadiumEntity location = stadiumsRepository.findById(Integer.parseInt(stadiumId)).orElseThrow(() -> new NotFoundException(404, "The stadium ID : " + stadiumId + " doesn't exist on the database."));
        TeamEntity team1 = teamsRepository.findById(Integer.parseInt(team1Id)).orElseThrow(() -> new NotFoundException(404, "The team 1 ID : " + team1Id + " doesn't exist on the database."));
        TeamEntity team2 = teamsRepository.findById(Integer.parseInt(team2Id)).orElseThrow(() -> new NotFoundException(404, "The team 2 ID : " + team2Id + " doesn't exist on the database."));
        if(location == null || team1 == null || team2 == null) {
            return null;
        }
        MatchEntity result = new MatchEntity();
        result.setId(match.getId());
        result.setLocation(location);
        result.setScore1(match.getScore1());
        result.setScore2(match.getScore2());
        result.setTeam1(team1);
        result.setTeam2(team2);
        return result;
    }
}
