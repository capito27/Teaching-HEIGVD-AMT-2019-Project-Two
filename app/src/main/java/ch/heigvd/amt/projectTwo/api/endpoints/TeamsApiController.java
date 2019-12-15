package ch.heigvd.amt.projectTwo.api.endpoints;


import ch.heigvd.amt.projectTwo.api.TeamsApi;
import ch.heigvd.amt.projectTwo.api.model.Team;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
import ch.heigvd.amt.projectTwo.repositories.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TeamsApiController implements TeamsApi {

    @Autowired
    TeamsRepository teamsRepository;

    @Override
    public ResponseEntity<List<Team>> getTeamsByUser() {
        List<Team> teams = new ArrayList<>();
        for(TeamEntity team : teamsRepository.findAll()){
            teams.add(toTeam(team));
        }
        return ResponseEntity.ok(teams);
    }

    private Team toTeam(TeamEntity team) {
        Team result = new Team();
        result.setId(team.getId());
        result.setName(team.getName());
        result.setCountry(team.getCountry());
        return result;
    }
}
