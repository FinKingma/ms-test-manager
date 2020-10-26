package io.componenttesting.teammorale.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.vo.TeamEvent;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamMoraleService {

    @Autowired
    private TeamsDao teamsDao;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handleNewHappening(String content) throws JsonProcessingException {
        TeamEvent event = objectMapper.readValue(content, TeamEvent.class);
        Optional<TeamsEntity> entity = teamsDao.findById(event.getId());

        if (entity.isPresent()) {
            entity.get().setName(event.getTeamName());
            entity.get().setVision(event.getHappening());
            teamsDao.save(entity.get());
        } else {
            throw new Error("team not found");
        }

    }
}
