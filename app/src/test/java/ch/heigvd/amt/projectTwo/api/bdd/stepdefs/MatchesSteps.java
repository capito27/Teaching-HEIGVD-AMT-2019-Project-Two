package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;


import ch.heigvd.amt.projectTwo.api.model.Match;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;

import java.util.List;

public class MatchesSteps extends AbstractSteps implements En {
    private String authorization = "";

    private String matchesUrl = "/api/app/matches";
    public MatchesSteps() {
        Given("user is authenticated" , () -> this.authorization = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLmNvIiwiYWRtaW4iOnRydWUsImp0aSI6NiwiaWF0IjoxNTc3NjQwNzE4LCJleHAiOjE1Nzc2NDQzMTh9.a_wQm7ESLCre1gHBlDB0F1aqfhIzcNOmAUAmwpQJ_04");
        When("^user get matches$", () -> executeGet(matchesUrl, this.authorization));
        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) -> {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));

            executePut(matchesUrl + "/" + matchId, this.authorization);
        });

    }
}
