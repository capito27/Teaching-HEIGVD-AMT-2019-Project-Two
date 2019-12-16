package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.UsersApi;
import ch.heigvd.amt.projectTwo.api.model.User;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<List<User>> showUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), true).map(UsersApiController::toUser).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    static private User toUser(UserEntity userEntity) {
        User result = new User();
        result.setEmail(userEntity.getEmail());
        result.setFirstname(userEntity.getFirstname());
        result.setLastname(userEntity.getLastname());
        return result;
    }
}
