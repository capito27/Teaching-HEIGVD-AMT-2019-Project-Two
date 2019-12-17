package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import ch.heigvd.amt.projectTwo.api.model.User;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.List;

import cucumber.api.java8.En;

public class LoginSteps extends AbstractSteps implements En {
    public LoginSteps() {
        Given("user wants to login with the following attributes", (DataTable userDt) -> {
            // initial request, reset context
            testContext().reset();

            List<UserLogin> userList = userDt.asList(UserLogin.class);
            super.testContext().setPayload(userList.get(0));
        });
        When("user login {string}", (String testContext) -> {
            String loginUrl = "/api/auth/login";

            executePost(loginUrl);
        });
        Then("the login {string}", (String expectedResult) -> {
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
    }
}
