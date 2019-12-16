package ch.heigvd.amt.projectTwo.api.endpoints;


import ch.heigvd.amt.projectTwo.api.TeamsApi;
import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.api.model.Team;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import ch.heigvd.amt.projectTwo.repositories.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class TeamsApiController implements TeamsApi {

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<List<Team>> getTeamsByUser() {
        List<Team> teams = teamsRepository.findAllByUserId((Integer) httpServletRequest.getAttribute("user_id")).parallelStream()
                .map(TeamsApiController::toTeam).collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    private static Team toTeam(TeamEntity team) {
        Team result = new Team();
        result.setId(team.getId());
        result.setName(team.getName());
        result.setCountry(team.getCountry());
        return result;
    }
}
