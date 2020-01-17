package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.RegisterApi;
import ch.heigvd.amt.projectTwo.api.model.UserFull;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Controller
public class RegisterApiController implements RegisterApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    private Logger logger = LoggerFactory.getLogger(UsersApiController.class);

    @Override
    public ResponseEntity<Void> registerPost(@Valid UserFull userFull) {
        if (!(Boolean) httpServletRequest.getAttribute("user_admin"))
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

        // Email already exists
        if (userRepository.countByEmail(userFull.getEmail()) != 0) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        UserEntity newUser = userFullToUserEntity(userFull);
        userRepository.save(newUser);
        logger.info("Created new user with mail : " + newUser.getEmail() + " and ID : " + newUser.getId());
        //TODO: URI to change
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    private UserEntity userFullToUserEntity(UserFull userFull) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(DigestUtils.sha256Hex(userFull.getPassword()));
        userEntity.setAdmin(userFull.getIsAdmin());
        userEntity.setEmail(userFull.getEmail());
        userEntity.setFirstname(userFull.getFirstname());
        userEntity.setLastname(userFull.getLastname());
        return userEntity;
    }
}
