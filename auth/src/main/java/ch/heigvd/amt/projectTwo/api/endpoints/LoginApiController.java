package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.LoginApi;
import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import ch.heigvd.amt.projectTwo.security.JwtTokenProvider;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    /*
        public ResponseEntity<Object> createFruit(@ApiParam(value = "", required = true) @Valid @RequestBody Fruit fruit) {
            FruitEntity newFruitEntity = toFruitEntity(fruit);
            fruitRepository.save(newFruitEntity);
            Long id = newFruitEntity.getId();

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newFruitEntity.getId()).toUri();

            return ResponseEntity.created(location).build();
        }


        public ResponseEntity<List<Fruit>> getFruits() {
            List<Fruit> fruits = new ArrayList<>();
            for (FruitEntity fruitEntity : fruitRepository.findAll()) {
                fruits.add(toFruit(fruitEntity));
            }

            Fruit staticFruit = new Fruit();
            staticFruit.setColour("red");
            staticFruit.setKind("banana");
            staticFruit.setSize("medium");
            List<Fruit> fruits = new ArrayList<>();
            fruits.add(staticFruit);

            return ResponseEntity.ok(fruits);
        }


        private FruitEntity toFruitEntity(Fruit fruit) {
            FruitEntity entity = new FruitEntity();
            entity.setColour(fruit.getColour());
            entity.setKind(fruit.getKind());
            entity.setSize(fruit.getSize());
            return entity;
        }

        private Fruit toFruit(FruitEntity entity) {
            Fruit fruit = new Fruit();
            fruit.setColour(entity.getColour());
            fruit.setKind(entity.getKind());
            fruit.setSize(entity.getSize());
            return fruit;
        }
        */
    private UserEntity toUserEntity(UserLogin user) {
        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }

}
