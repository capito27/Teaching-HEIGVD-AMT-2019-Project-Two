package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import ch.heigvd.amt.projectTwo.api.model.AdminPasswordChange;
import ch.heigvd.amt.projectTwo.api.model.UserFull;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import ch.heigvd.amt.projectTwo.api.model.UserPasswordChange;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class Steps extends AbstractSteps implements En {
    protected String loginUrl = "/api/auth/login";
    protected String usersUrl = "/api/auth/users";
    protected String registerUrl = "/api/auth/register";

    // Present to remove test users once created
    @Autowired
    private UserRepository userRepository;

    private UserPasswordChange latestUserPasswordChange = null;

    public Steps() {
        Given("^user is authenticated as email \"([^\"]*)\" with password \"([^\"]*)\"$", (String mail, String password) -> {
            // initial request, reset context
            testContext().reset();
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(mail);
            userLogin.setPassword(password);

            super.testContext().setPayload(userLogin);

            executePost(loginUrl);

            Response response = testContext().getResponse();
            this.authorization = response.getHeader("authorization");
        });
        Given("user wants to login with the following attributes", (DataTable userDt) -> {
            // initial request, reset context
            testContext().reset();

            List<UserLogin> userList = userDt.asList(UserLogin.class);
            super.testContext().setPayload(userList.get(0));
        });

        When("user login {string}", (String testContext) -> executePost(loginUrl));

        When("^register with the following attributes$", (DataTable userFullDt) -> {
            List<UserFull> userList = userFullDt.asList(UserFull.class);
            super.testContext().setPayload(userList.get(0));

            executePost(registerUrl, this.authorization);
        });

        When("^user gets users$", () -> executeGet(usersUrl, this.authorization));

        When("^user update password with the following attributes$", (DataTable userPasswordChangeDt) -> {

            List<UserPasswordChange> userPasswordChangeList = userPasswordChangeDt.asList(UserPasswordChange.class);
            super.testContext().setPayload(userPasswordChangeList.get(0));

            latestUserPasswordChange = userPasswordChangeList.get(0);

            executePut(usersUrl, this.authorization);
        });

        When("^admin update \"([^\"]*)\" password with the following attributes$", (String email, DataTable adminPasswordChangeDt) -> {
            List<AdminPasswordChange> adminPasswordChangeList = adminPasswordChangeDt.asList(AdminPasswordChange.class);
            super.testContext().setPayload(adminPasswordChangeList.get(0));

            executePut(usersUrl + "/" + email, this.authorization);
        });

        Then("^revert user password update$", () -> {
            // swaps the old and new pass around in place, thanks to the redundant field
            latestUserPasswordChange.setRepeatPass(latestUserPasswordChange.getOldPass());
            latestUserPasswordChange.setOldPass(latestUserPasswordChange.getNewPass());
            latestUserPasswordChange.setNewPass(latestUserPasswordChange.getRepeatPass());

            super.testContext().setPayload(latestUserPasswordChange);

            executePut(usersUrl, this.authorization);


            Response response = testContext().getResponse();
            assertThat(response.getStatusCode() == 200);
        });


        Then("^revert admin password update of user \"([^\"]*)\" to \"([^\"]*)\"$", (String email, String password) -> {
            // swaps the old and new pass around in place, thanks to the redundant field
            AdminPasswordChange passwordChange = new AdminPasswordChange();
            passwordChange.newPass(password);
            passwordChange.repeatPass(password);

            super.testContext().setPayload(passwordChange);

            executePut(usersUrl + "/" + email, this.authorization);

            Response response = testContext().getResponse();
            assertThat(response.getStatusCode() == 200);

        });

        Then("^user can authenticate as email \"([^\"]*)\" with password \"([^\"]*)\"$", (String mail, String password) -> {
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(mail);
            userLogin.setPassword(password);

            super.testContext().setPayload(userLogin);

            executePost(loginUrl);

            Response response = testContext().getResponse();
            assertThat(response.getStatusCode()).isIn(200, 201);
            assertThat(response.getHeader("authorization")).isNotNull().isNotBlank();
        });

        Then("^user can't authenticate as email \"([^\"]*)\" with password \"([^\"]*)\"$", (String mail, String password) -> {
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(mail);
            userLogin.setPassword(password);

            super.testContext().setPayload(userLogin);

            executePost(loginUrl);

            Response response = testContext().getResponse();
            assertThat(response.getStatusCode()).isBetween(400, 504);
        });

        Then("^remove user with email \"([^\"]*)\"$", (String mail) -> {
            UserEntity userEntity = userRepository.findByEmail(mail);
            assertThat(userEntity).isNotNull();
            userRepository.delete(userEntity);
        });

        Then("the request {string}", (String expectedResult) -> {
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
