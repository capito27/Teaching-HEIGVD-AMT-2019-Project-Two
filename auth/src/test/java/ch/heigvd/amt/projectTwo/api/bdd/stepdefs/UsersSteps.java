package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import ch.heigvd.amt.projectTwo.api.model.AdminPasswordChange;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import ch.heigvd.amt.projectTwo.api.model.UserPasswordChange;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.List;

import cucumber.api.java8.En;
import org.springframework.http.HttpHeaders;

public class UsersSteps extends AbstractSteps implements En {
    private String authorization = "";

    private String usersUrl = "/api/auth/users";
    private String loginUrl = "/api/auth/login";

    private UserPasswordChange latestUserPasswordChange = null;

    public UsersSteps() {

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

        Then("^user can authenticate as email \"([^\"]*)\" with password \"([^\"]*)\"$", (String mail, String password) -> {
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(mail);
            userLogin.setPassword(password);

            super.testContext().setPayload(userLogin);

            executePost(loginUrl);

            Response response = testContext().getResponse();
            assertThat(response.getStatusCode() == 200 && response.getHeader(HttpHeaders.AUTHORIZATION) != null && !response.getHeader(HttpHeaders.AUTHORIZATION).isEmpty());
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


    }
}
