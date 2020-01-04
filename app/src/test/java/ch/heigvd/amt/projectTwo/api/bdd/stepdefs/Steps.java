package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import ch.heigvd.amt.projectTwo.api.model.*;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Steps extends AbstractSteps implements En {
    private String authorization="";
    private Integer stadiumAdded;
    private Integer teamAdded;
    private Integer matchAdded;

    @Autowired
    private JwtTokenProvider provider;

    private String stadiumsUrl = "/api/app/stadiums";
    private String matchesUrl = "/api/app/matches";
    private String cancellationsUrl = "/api/app/cancellations";
    private String teamsUrl = "/api/app/teams";

    public Steps() {
        Given("user is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, false, id);
        });
        Given("admin is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, true, id);
        });

        When("^user get matches$", () -> {
            executeGet(matchesUrl, this.authorization);
        });

        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) ->

        {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(matchId));
            executePut(matchesUrl + "/" + matchId, this.authorization);
        });
        Then("^we can cancel this match$", () -> {
            Response response = testContext().getResponse();
            MatchDetails added = response.getBody().as(MatchDetails.class);
            matchAdded = added.getId();
            super.testContext().setPayload(added);
            executePost(cancellationsUrl, this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add match with following attributes$", (DataTable matchDt) -> {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            executePost(matchesUrl, this.authorization);
        });

        When("^user get match (\\d+)$", (Integer matchId) -> {
            executeGet(matchesUrl + "/" + matchId, this.authorization);
        });
        Then("^we can get this match$", () -> {
            Response response = testContext().getResponse();
            MatchDetails added = response.getBody().as(MatchDetails.class);
            matchAdded = added.getId();
            executeGet(matchesUrl + "/" + matchAdded, this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
        When("^user get stadiums$", () -> {
            // Due to the large number of stadiums, we can't display them all, so we don't log the body of the response
            executeGet(stadiumsUrl, this.authorization);
        });

        When("^user update stadium (\\d+) with the following attributes$", (Integer stadiumId, DataTable stadiumDt) ->
        {
            List<Stadium> stadiumList = stadiumDt.asList(Stadium.class);
            super.testContext().setPayload(stadiumList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(stadiumId));
            executePut(stadiumsUrl + "/" + stadiumId, this.authorization);
        });
        Then("^we can delete this stadium$", () -> {
            Response response = testContext().getResponse();
            StadiumDetails added = response.getBody().as(StadiumDetails.class);
            stadiumAdded = added.getId();
            executeDelete(stadiumsUrl + "/" + stadiumAdded, this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add stadium with following attributes$", (DataTable stadiumDt) -> {
            List<Stadium> stadiumList = stadiumDt.asList(Stadium.class);
            super.testContext().setPayload(stadiumList.get(0));
            executePost(stadiumsUrl, this.authorization);
        });

        When("^user get stadium (\\d+)$", (Integer stadiumId) -> {
            executeGet(stadiumsUrl + "/" + stadiumId, this.authorization);
        });
        Then("^we can get this stadium$", () -> {
            Response response = testContext().getResponse();
            StadiumDetails added = response.getBody().as(StadiumDetails.class);
            stadiumAdded = added.getId();
            executeGet(stadiumsUrl + "/" + stadiumAdded, this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
        When("^user get teams$", () -> {
            // Due to the large number of teams, we can't display them all, so we don't log the body of the response
            executeGet(teamsUrl, this.authorization);
        });

        When("^user update team (\\d+) with the following attributes$", (Integer teamId, DataTable teamDt) ->
        {
            List<Team> teamList = teamDt.asList(Team.class);
            super.testContext().setPayload(teamList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(teamId));
            executePut(teamsUrl + "/" + teamId, this.authorization);
        });
        Then("^we can delete this team$", () -> {
            Response response = testContext().getResponse();
            TeamDetails added = response.getBody().as(TeamDetails.class);
            teamAdded = added.getId();
            executeDelete(teamsUrl + "/" + teamAdded, this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add team with following attributes$", (DataTable teamDt) -> {
            List<Team> teamList = teamDt.asList(Team.class);
            super.testContext().setPayload(teamList.get(0));
            executePost(teamsUrl, this.authorization);
        });

        When("^user get team (\\d+)$", (Integer teamId) -> {
            executeGet(teamsUrl + "/" + teamId, this.authorization);
        });
        Then("^we can get this team$", () -> {
            Response response = testContext().getResponse();
            TeamDetails added = response.getBody().as(TeamDetails.class);
            teamAdded = added.getId();
            executeGet(teamsUrl + "/" + teamAdded, this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
    }
}
