package io.componenttesting.teammorale.endpoint;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.service.TeamMoraleService;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class TeamMoraleEndpoint {

    @Autowired
    TeamsDao teamsDao;

    @Autowired
    TeamMoraleService teamMoraleService;

    @GetMapping("/{name}")
    public TeamsEntity getByName(@PathVariable String name) {
        Optional<TeamsEntity> result = teamsDao.findByName(name);
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
        teamMoraleService.createNewTeam(team);
    }

    @PutMapping()
    public void updateTeam(@RequestBody @Valid Team team) {
        teamMoraleService.updateTeam(team);
    }
}
