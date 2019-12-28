package ch.heigvd.amt.projectTwo.api.endpoints;


import ch.heigvd.amt.projectTwo.api.TeamsApi;
import ch.heigvd.amt.projectTwo.api.model.Team;
import ch.heigvd.amt.projectTwo.api.model.TeamDetails;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import ch.heigvd.amt.projectTwo.repositories.TeamsRepository;
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


@Controller
public class TeamsApiController implements TeamsApi {

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(TeamsApiController.class);

    @Override
    public ResponseEntity<Void> updateTeam(Integer teamId, @Valid Team team) {
        return null;
    }

    @Override
    public ResponseEntity<List<TeamDetails>> getTeamsByUser() {
        List<TeamDetails> teams = teamsRepository.findAllByUserId((Integer) httpServletRequest.getAttribute("user_id")).parallelStream()
                .map(TeamsApiController::toTeamDetails).collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @Override
    public ResponseEntity<Team> getTeamById(Integer teamId) {
        TeamEntity teamEntity = teamsRepository.findById(teamId).orElse(null);
        if(teamEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(teamEntity.getUserId() != (Integer)  httpServletRequest.getAttribute("user_id")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Team teams = toTeam(teamEntity);
        return ResponseEntity.ok().body(teams);
    }

    @Override
    public ResponseEntity<Void> addTeam(@Valid Team team) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTeam(Integer teamId) {
        return null;
    }

    private static TeamDetails toTeamDetails(TeamEntity team) {
        TeamDetails result = new TeamDetails();
        result.setId(team.getId());
        result.setName(team.getName());
        result.setCountry(team.getCountry());
        return result;
    }
    private static Team toTeam(TeamEntity team) {
        Team result = new Team();
        result.setName(team.getName());
        result.setCountry(team.getCountry());
        return result;
    }
}
