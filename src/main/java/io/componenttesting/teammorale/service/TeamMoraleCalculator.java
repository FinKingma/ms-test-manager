package io.componenttesting.teammorale.service;

import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.vo.TeamEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class TeamMoraleCalculator {

    public BigDecimal getUpdatedHappiness(TeamsEntity entity, TeamEvent event) {
        if (entityIsLaggard(entity)) {
            return event.getFun();
        } else {
            BigDecimal originalHappiness = entity.getHappiness().multiply(new BigDecimal(entity.getNumberOfUpdates()));
            BigDecimal newTotalHappiness = originalHappiness.add(event.getFun().multiply(new BigDecimal(2)));
            BigDecimal newAvgHappiness = newTotalHappiness.divide(new BigDecimal(entity.getNumberOfUpdates()).add(new BigDecimal(2)), RoundingMode.HALF_UP);
            return newAvgHappiness;
        }
    }

    public BigDecimal getUpdatedMorale(TeamsEntity entity, TeamEvent event) {
        BigDecimal eventMorale = event.getFun().add(event.getLearned()).divide(new BigDecimal(2));
        if (entityIsLaggard(entity)) {
            return eventMorale;
        } else {
            BigDecimal originalMorale = entity.getMorale().multiply(new BigDecimal(entity.getNumberOfUpdates()));
            BigDecimal newTotalMorale = originalMorale.add(eventMorale.multiply(new BigDecimal(2)));
            BigDecimal newAvgMorale = newTotalMorale.divide(new BigDecimal(entity.getNumberOfUpdates()).add(new BigDecimal(2)), RoundingMode.HALF_UP);
            return newAvgMorale;
        }
    }

    private boolean entityIsLaggard(TeamsEntity entity) {
        return entity.getLastUpdatedOn().isBefore(LocalDateTime.now().minusMonths(6));
    }
}
