package io.componenttesting.teammorale.endpoint;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.service.TeamMoraleService;
import io.componenttesting.teammorale.vo.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class TeamMoraleEndpoint {

    @Autowired
    TeamsDao teamsDao;

    @GetMapping("/{id}")
    public Team getById(@PathVariable Integer id) {
        Optional<TeamsEntity> result = teamsDao.findById(id);
        if (result.isPresent()) {
            Team team = new Team();
            team.setName(result.get().getName());
            return team;
        } else {
            throw new Error("AAHHHH");
        }
    }

    @GetMapping()
    public List<TeamsEntity> getAll() {
        List<TeamsEntity> result = teamsDao.findAll();
        return result;
    }
}
