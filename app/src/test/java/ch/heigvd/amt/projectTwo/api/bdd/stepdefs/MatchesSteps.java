package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;


import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MatchesSteps extends AbstractSteps implements En {
    private String authorization = "";

    @Autowired
    private JwtTokenProvider provider;

    private String matchesUrl = "/api/app/matches";

    public MatchesSteps() {
        Given("user is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, false, id);
        });
        When("^user get matches$", () -> {
            // Due to the large number of matches, we can't display them all, so we don't log the body of the response
            setDisplayFullBodyLog(false);
            executeGet(matchesUrl, this.authorization);
            setDisplayFullBodyLog(true);
        });

        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) ->

        {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            Logger logger = LoggerFactory.getLogger(MatchesSteps.class);

            logger.debug(Integer.toString(matchId));
            executePut(matchesUrl + "/" + matchId, this.authorization);
        });

    }
}
