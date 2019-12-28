package ch.heigvd.amt.projectTwo.api.endpoints;

import ch.heigvd.amt.projectTwo.api.CancellationsApi;
import ch.heigvd.amt.projectTwo.api.model.Match;
import ch.heigvd.amt.projectTwo.api.model.MatchDetails;
import ch.heigvd.amt.projectTwo.repositories.MatchesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CancellationsApiController implements CancellationsApi {
    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(CancellationsApiController.class);

    @Override
    public ResponseEntity<Void> deleteMatch(@Valid MatchDetails matchDetails) {
        return null;
    }
}
