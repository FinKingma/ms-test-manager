package io.componenttesting.teammorale.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.endpoint.TeamMoraleEndpoint;
import io.componenttesting.teammorale.event.EventPublisher;
import io.componenttesting.teammorale.event.EventReceiver;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TeamMoraleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMoraleService.class);

    @Autowired
    private TeamsDao teamsDao;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private TeamMoraleCalculator teamMoraleCalculator;

    private final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public void handleNewHappening(String teamName, String content) throws JsonProcessingException {
        TeamEvent event = objectMapper.readValue(content, TeamEvent.class);
        Optional<TeamsEntity> entity = teamsDao.findByNameIgnoreCase(teamName);

        if (entity.isPresent()) {
            updateTeamBasedOnEvent(entity.get(), event);
            eventPublisher.publishMessageEvent(teamName, "team has been updated");
        } else {
            LOGGER.info("team {} does not exist, please use our amazing api to create a new team before using the event platform", teamName);
        }
    }

    private void updateTeamBasedOnEvent(TeamsEntity entity, TeamEvent event) {
        LOGGER.info("updating team {}", event.getTeamName());
        BigDecimal newHappiness = teamMoraleCalculator.getUpdatedHappiness(entity, event);
        BigDecimal newMorale = teamMoraleCalculator.getUpdatedMorale(entity, event);
        entity.setHappiness(newHappiness);
        entity.setMorale(newMorale);
        entity.setLastUpdatedOn(LocalDateTime.now());
        entity.setNumberOfUpdates(entity.getNumberOfUpdates()+1);
        teamsDao.save(entity);
    }

    public void createNewTeam(Team team) {
        if (team.getMorale() == null || team.getHappiness() == null) {
            throw new Error("object is missing morale / happiness: " + team);
        } else if (teamsDao.findByNameIgnoreCase(team.getTeamName()).isPresent()) {
            throw new Error("Team already exists, please use a unique team name");
        } else {
            TeamsEntity newEntity = new TeamsEntity();
            newEntity.setName(team.getTeamName());
            newEntity.setVision(team.getVision());
            newEntity.setHappiness(team.getHappiness());
            newEntity.setMorale(team.getMorale());
            newEntity.setNumberOfUpdates(1);
            newEntity.setCreatedOn(LocalDateTime.now());
            newEntity.setLastUpdatedOn(LocalDateTime.now());

            teamsDao.save(newEntity);

            LOGGER.info("new team {} added", team.getTeamName());
        }
    }

    public void updateTeam(Team team) {
        Optional<TeamsEntity> entity = teamsDao.findByNameIgnoreCase(team.getTeamName());
        if (entity.isPresent()) {
            TeamsEntity updatedEntity = entity.get();

            updatedEntity.setVision(team.getVision());
            updatedEntity.setLastUpdatedOn(LocalDateTime.now());
            teamsDao.save(updatedEntity);
        } else {
            throw new Error("Team does not exist");
        }
    }
}
