package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;


import ch.heigvd.amt.projectTwo.api.model.Stadium;
import ch.heigvd.amt.projectTwo.api.model.StadiumDetails;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StadiumsSteps extends AbstractSteps implements En {
    private String authorization = "";
    private Integer stadiumAdded;

    @Autowired
    private JwtTokenProvider provider;

    private String stadiumsUrl = "/api/app/stadiums";

    public StadiumsSteps() {
        When("^user get stadiums$", () -> {
            // Due to the large number of stadiums, we can't display them all, so we don't log the body of the response
            setDisplayFullBodyLog(false);
            executeGet(stadiumsUrl, this.authorization);
            setDisplayFullBodyLog(true);
        });

        When("^user update stadium (\\d+) with the following attributes$", (Integer stadiumId, DataTable stadiumDt) ->
        {
            List<Stadium> stadiumList = stadiumDt.asList(Stadium.class);
            super.testContext().setPayload(stadiumList.get(0));
            Logger logger = LoggerFactory.getLogger(StadiumsSteps.class);

            logger.debug(Integer.toString(stadiumId));
            executePut(stadiumsUrl + "/" + stadiumId, this.authorization);
        });
        Then("^we can delete this stadium$", () -> {
            Response response = testContext().getResponse();
            StadiumDetails added = response.getBody().as(StadiumDetails.class);
            stadiumAdded = added.getId();
            super.testContext().setPayload(added);
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
    }
}
