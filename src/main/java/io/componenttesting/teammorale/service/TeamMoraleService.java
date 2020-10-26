package io.componenttesting.teammorale.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.event.EventPublisher;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamMoraleService {

    @Autowired
    private TeamsDao teamsDao;

    @Autowired
    private EventPublisher eventPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public void handleNewHappening(String content) throws JsonProcessingException {
        TeamEvent event = objectMapper.readValue(content, TeamEvent.class);
        Optional<TeamsEntity> entity = teamsDao.findByName(event.getTeamName());

        if (entity.isPresent()) {
            entity.get().setName(event.getTeamName());
            entity.get().setVision(event.getHappening());
            teamsDao.save(entity.get());
        } else {
            TeamsEntity newEntity = new TeamsEntity();
            newEntity.setName(event.getTeamName());
            newEntity.setVision(event.getHappening());
            teamsDao.save(newEntity);
        }

        eventPublisher.publishMessageEvent("yihaa");
    }
}
