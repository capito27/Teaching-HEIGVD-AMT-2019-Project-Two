package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.LoginApi;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class LoginApiController implements LoginApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<Void> login(@Valid UserLogin userLogin) {
        try {
            String token = jwtTokenProvider.createToken(userLogin);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
        } catch (IllegalArgumentException ignored) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    }

    private UserEntity toUserEntity(UserLogin user) {
        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }

}
