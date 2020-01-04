package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;


import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.api.model.MatchDetails;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchesSteps extends AbstractSteps implements En {

    private String authorization="";

    private Integer matchAdded;
    @Autowired
    private JwtTokenProvider provider;

    private String matchesUrl = "/api/app/matches";
    private String cancellationsUrl = "/api/app/cancellations";
    public MatchesSteps() {
        Given("user is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, false, id);
        });

        When("^user get matches$", () -> {
            executeGet(matchesUrl, this.authorization);
        });

        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) ->

        {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            Logger logger = LoggerFactory.getLogger(MatchesSteps.class);

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
    }
}
