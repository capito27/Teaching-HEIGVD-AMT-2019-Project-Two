package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.UsersApi;
import ch.heigvd.amt.projectTwo.api.model.AdminPasswordChange;
import ch.heigvd.amt.projectTwo.api.model.User;
import ch.heigvd.amt.projectTwo.api.model.UserPasswordChange;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import ch.heigvd.amt.projectTwo.security.JwtTokenFilter;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    private Logger logger = LoggerFactory.getLogger(UsersApiController.class);

    @Override
    public ResponseEntity<List<User>> showUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), true).map(UsersApiController::toUser).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<Void> changePassword(@Valid UserPasswordChange passwordChange) {
        UserEntity userEntity = userRepository.findByEmail((String) httpServletRequest.getAttribute("user_mail"));

        if (!passwordChange.getNewPass().equals(passwordChange.getRepeatPass())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // if the new password and the repeat password match, as well as the old password matching the one on the database, we update the entity
        if (DigestUtils.sha256Hex(passwordChange.getOldPass()).equals(userEntity.getPassword())) {
            userEntity.setPassword(DigestUtils.sha256Hex(passwordChange.getNewPass()));
            userRepository.save(userEntity);
            logger.info("Changed password for user : " + userEntity.getEmail());
            return ResponseEntity.ok().build();
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @Override
    public ResponseEntity<Void> adminChangePassword(String userID, @Valid AdminPasswordChange adminPasswordChange) {
        if (!(Boolean) httpServletRequest.getAttribute("user_admin"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        UserEntity userEntity = userRepository.findByEmail(userID);

        if (userEntity == null || !adminPasswordChange.getNewPass().equals(adminPasswordChange.getRepeatPass())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(DigestUtils.sha256Hex(adminPasswordChange.getNewPass()));
        userRepository.save(userEntity);
        logger.info("Admin (" + httpServletRequest.getAttribute("user_mail") + ") Changed password for user : " + userEntity.getEmail());
        return ResponseEntity.ok().build();
    }

    static private User toUser(UserEntity userEntity) {
        User result = new User();
        result.setEmail(userEntity.getEmail());
        result.setFirstname(userEntity.getFirstname());
        result.setLastname(userEntity.getLastname());
        return result;
    }
}
