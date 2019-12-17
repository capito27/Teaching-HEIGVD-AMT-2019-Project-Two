package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import ch.heigvd.amt.projectTwo.api.model.User;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cucumber.api.java8.En;

public class UsersSteps extends AbstractSteps implements En {
    private String authorization = "";

    public UsersSteps() {

        Given("^user is authenticated as email \"([^\"]*)\" with password \"([^\"]*)\"$", (String mail, String password) -> {
            // initial request, reset context
            testContext().reset();
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(mail);
            userLogin.setPassword(password);

            super.testContext().setPayload(userLogin);

            String loginUrl = "/api/auth/login";

            executePost(loginUrl);

            Response response = testContext().getResponse();
            this.authorization = response.getHeader("authorization");
        });
        When("^user gets users$", () -> {
            String usersUrl = "/api/auth/users";

            executeGet(usersUrl, this.authorization);
        });
        Then("the get {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();

            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

        Then("^response is not empty$", () -> {
            Response response = testContext().getResponse();
            assertThat(!response.getBody().asString().isEmpty());
        });
    }
}
