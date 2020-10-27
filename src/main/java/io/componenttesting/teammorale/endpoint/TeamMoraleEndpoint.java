package io.componenttesting.teammorale.endpoint;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.service.TeamMoraleService;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class TeamMoraleEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMoraleEndpoint.class);

    @Autowired
    TeamsDao teamsDao;

    @Autowired
    TeamMoraleService teamMoraleService;

    @GetMapping("/{name}")
    public TeamsEntity getByName(@PathVariable String name) {
        Optional<TeamsEntity> result = teamsDao.findByNameIgnoreCase(name);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new Error("did not find team " + name);
        }
    }

    @GetMapping()
    public List<TeamsEntity> getAll() {
        List<TeamsEntity> result = teamsDao.findAll();
        return result;
    }

    @PostMapping()
    public void createNewTeam(@RequestBody @Valid Team team) {
        try {
            teamMoraleService.createNewTeam(team);
        } catch (Error e) {
            throw new InvalidArgumentException();
        }
    }

    @PutMapping()
    public void updateTeam(@RequestBody @Valid Team team) {
        try {
            teamMoraleService.updateTeam(team);
        } catch (Error e) {
            throw new InvalidArgumentException();
        }
    }

    @DeleteMapping("/{name}")
    public void deleteTeam(@PathVariable String name) {
        Optional<TeamsEntity> result = teamsDao.findByNameIgnoreCase(name);
        if (result.isPresent()) {
            teamsDao.delete(result.get());
            LOGGER.info("team {} was deleted", name);
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public class InvalidArgumentException extends RuntimeException {
    }
}
