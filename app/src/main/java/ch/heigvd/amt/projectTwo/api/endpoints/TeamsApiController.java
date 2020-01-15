package ch.heigvd.amt.projectTwo.api.endpoints;


import ch.heigvd.amt.projectTwo.api.TeamsApi;
import ch.heigvd.amt.projectTwo.api.exceptions.ForbiddenException;
import ch.heigvd.amt.projectTwo.api.exceptions.NotFoundException;
import ch.heigvd.amt.projectTwo.api.model.Team;
import ch.heigvd.amt.projectTwo.api.model.TeamDetails;
import ch.heigvd.amt.projectTwo.entities.MatchEntity;
import ch.heigvd.amt.projectTwo.entities.TeamEntity;
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
import java.util.stream.StreamSupport;


@Controller
public class TeamsApiController implements TeamsApi {

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(TeamsApiController.class);

    @Override
    public ResponseEntity<List<TeamDetails>> getTeamsByUser() {
        List<TeamDetails> teams = StreamSupport.stream(teamsRepository.findAll().spliterator(), true).map(TeamsApiController::toTeamDetails).collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @Override
    public ResponseEntity<Team> getTeamById(Integer teamId) throws NotFoundException {
        TeamEntity teamEntity = teamsRepository.findById(teamId).orElseThrow(() -> new NotFoundException(404, "The team ID : " + teamId + " doesn't exist on the database."));
        Team teams = toTeam(teamEntity);
        return ResponseEntity.ok().body(teams);
    }

    @Override
    public ResponseEntity<TeamDetails> addTeam(@Valid Team team) throws ForbiddenException {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            throw new ForbiddenException("You are not an administrator");
        }
        TeamEntity newTeam = toTeamEntity(team);
        teamsRepository.save(newTeam);
        return ResponseEntity.status(HttpStatus.OK).body(toTeamDetails(newTeam));
    }

    @Override
    public ResponseEntity<Void> updateTeam(Integer teamId, @Valid Team team) throws ForbiddenException, NotFoundException {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            throw new ForbiddenException("You are not an administrator");
        }
        TeamEntity teamInDB = teamsRepository.findById(teamId).orElseThrow(() -> new NotFoundException(404, "The team ID : " + teamId + " doesn't exist on the database."));
        teamInDB.setCountry(team.getCountry());
        teamInDB.setName(team.getName());
        teamsRepository.save(teamInDB);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteTeam(Integer teamId) throws NotFoundException, ForbiddenException {
        if(!(Boolean) httpServletRequest.getAttribute("user_admin")){
            throw new ForbiddenException("You are not an administrator");
        }
        TeamEntity teamToDelete = teamsRepository.findById(teamId).orElseThrow(() -> new NotFoundException(404, "The team ID : " + teamId + " doesn't exist on the database."));
        teamsRepository.deleteById(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
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

    private static TeamEntity toTeamEntity(Team team) {
        TeamEntity result = new TeamEntity();
        result.setName(team.getName());
        result.setCountry(team.getCountry());
        return result;
    }
}
