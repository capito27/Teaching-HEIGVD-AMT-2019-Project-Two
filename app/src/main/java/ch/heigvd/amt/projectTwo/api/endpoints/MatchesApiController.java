package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.MatchesApi;
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
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class MatchesApiController implements MatchesApi {

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    StadiumsRepository stadiumsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(MatchesApiController.class);

    @Override
    public ResponseEntity<List<MatchDetails>> getMatchesByUser() {
        List<MatchDetails> matches = matchesRepository.findAllByUserId((Integer) httpServletRequest.getAttribute("user_id")).parallelStream()
                .map(MatchesApiController::toMatchDetails).collect(Collectors.toList());
        return ResponseEntity.ok(matches);
    }

    @Override
    public ResponseEntity<Match> getMatchById(Integer matchId) throws NotFoundException, ForbiddenException {
        MatchEntity matchEntity = matchesRepository.findById(matchId).orElseThrow(() -> new NotFoundException(404, "The match ID : " + matchId + " doesn't exist on the database."));
        if(matchEntity.getUserId() != (Integer) httpServletRequest.getAttribute("user_id")) {
            throw new ForbiddenException("You can't get this match, it doesn't belongs to you");
        }
        Match match = toMatch(matchEntity);
        return ResponseEntity.ok().body(match);
    }

    @Override
    public ResponseEntity<Void> addMatch(@Valid Match match) throws NotFoundException {
        MatchEntity newMatch = toMatchEntity(match);
        newMatch.setUserId((Integer) httpServletRequest.getAttribute("user_id"));
        matchesRepository.save(newMatch);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateMatch(Integer matchId, @Valid Match match) throws Exception{
        MatchEntity newMatch = toMatchEntity(match);
        MatchEntity matchInDB = matchesRepository.findById(matchId).orElseThrow(() -> new NotFoundException(404, "The match ID : " + matchId + " doesn't exist on the database."));
        if(matchInDB.getUserId() != (Integer) httpServletRequest.getAttribute("user_id")){
            throw new ForbiddenException("The match"+ matchId +" don't belong to you, so you can't modify it");
        }
        newMatch.setUserId((Integer) httpServletRequest.getAttribute("user_id"));
        newMatch.setId(matchId);
        matchesRepository.save(newMatch);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private static MatchDetails toMatchDetails(MatchEntity match) {
        MatchDetails result = new MatchDetails();
        result.setId(match.getId());
        result.setLocation(String.format("/stadiums/%d", match.getLocation().getId()));
        result.setScore1(match.getScore1());
        result.setScore2(match.getScore2());
        result.setTeam1(String.format("/teams/%d", match.getTeam1().getId()));
        result.setTeam2(String.format("/teams/%d", match.getTeam2().getId()));
        return result;
    }

    private static Match toMatch(MatchEntity match) {
        Match result = new Match();
        result.setLocation(String.format("/stadiums/%d", match.getLocation().getId()));
        result.setScore1(match.getScore1());
        result.setScore2(match.getScore2());
        result.setTeam1(String.format("/teams/%d", match.getTeam1().getId()));
        result.setTeam2(String.format("/teams/%d", match.getTeam2().getId()));
        return result;
    }

    private MatchEntity toMatchEntity(Match match) throws NotFoundException {
        String stadiumId = match.getLocation().split("/")[2];
        logger.info("Value from post req : " + stadiumId);
        logger.info("The match treated : " + match.toString());
        String team1Id = match.getTeam1().split("/")[2];
        String team2Id = match.getTeam2().split("/")[2];
        // TODO parseInt throws exception, catch them
        StadiumEntity location = stadiumsRepository.findById(Integer.parseInt(stadiumId)).orElseThrow(() -> new NotFoundException(404, "The stadium ID : " + stadiumId + " doesn't exist on the database."));
        TeamEntity team1 = teamsRepository.findById(Integer.parseInt(team1Id)).orElseThrow(() -> new NotFoundException(404, "The team1 ID : " + team1Id + " doesn't exist on the database."));
        TeamEntity team2 = teamsRepository.findById(Integer.parseInt(team2Id)).orElseThrow(() -> new NotFoundException(404, "The team2 ID : " + team2Id + " doesn't exist on the database."));
        MatchEntity result = new MatchEntity();
        result.setLocation(location);
        result.setScore1(match.getScore1());
        result.setScore2(match.getScore2());
        result.setTeam1(team1);
        result.setTeam2(team2);
        return result;
    }
}
