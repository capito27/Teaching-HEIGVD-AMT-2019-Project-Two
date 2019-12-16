package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.MatchesApi;
import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import ch.heigvd.amt.projectTwo.entities.StadiumEntity;
import ch.heigvd.amt.projectTwo.repositories.MatchesRepository;
import ch.heigvd.amt.projectTwo.repositories.StadiumsRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

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
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(MatchesApiController.class);

    @Override
    public ResponseEntity<List<Match>> getMatchesByUser() {
        logger.info(httpServletRequest.getAttribute("user_id").toString());
        List<Match> stadiums = matchesRepository.findAllByUserId((Integer) httpServletRequest.getAttribute("user_id")).parallelStream()
                .map(MatchesApiController::toMatch).collect(Collectors.toList());
        return ResponseEntity.ok(stadiums);
    }

    private static Match toMatch(MatchEntity match) {
        Match result = new Match();
        result.setId(match.getId());
        //result.setLocation(String.format("/stadiums/%d", match.getStadium().getId()));
        result.setScore1(match.getScore1());
        result.setScore2(match.getScore2());
        result.setTeam1(String.format("/teams/%d", match.getTeam1().getId()));
        //result.setTeam2(String.format("/teams/%d", match.getTeam2().getId()));
        return result;
    }
}
