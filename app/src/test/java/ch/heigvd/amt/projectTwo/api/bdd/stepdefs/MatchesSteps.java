package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;


import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.api.model.MatchDetails;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchesSteps extends AbstractSteps implements En {
    private String authorization = "";
    private Integer matchAdded;

    private String matchesUrl = "/api/app/matches";
    private String cancellationsUrl = "/api/app/cancellations";
    public MatchesSteps() {
        Given("user is authenticated" , () -> this.authorization = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLmNvIiwiYWRtaW4iOnRydWUsImp0aSI6NiwiaWF0IjoxNTc3NjUyMDg4LCJleHAiOjE1Nzc2NTU2ODh9.rDspZQLV5YqDlv4yKbIAdw-tUPmFDkLKs-ApoTlLqhg");
        When("^user get matches$", () -> executeGet(matchesUrl, this.authorization));
        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) -> {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));

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
    }
}
