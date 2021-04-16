package io.componenttesting.testmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.event.EventPublisher;
import io.componenttesting.testmanager.model.ProjectEntity;
import io.componenttesting.testmanager.vo.Project;
import io.componenttesting.testmanager.vo.TestDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EventPublisher eventPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public void handleNewHappening(String projectName, String content) throws JsonProcessingException {
        TestDataEvent event = objectMapper.readValue(content, TestDataEvent.class);
        Optional<ProjectEntity> entity = projectDao.findByNameIgnoreCase(projectName);

        if (entity.isPresent()) {
            updateTeamBasedOnEvent(entity.get(), event);
            eventPublisher.publishMessageEvent(projectName, "team has been updated");
        } else {
            LOGGER.info("project {} does not exist, please use our amazing api to create a new project before using the event platform", projectName);
        }
    }

    private void updateTeamBasedOnEvent(ProjectEntity entity, TestDataEvent event) {
        LOGGER.info("updating project {}", event.getTeamName());

        projectDao.save(entity);
    }

    public void createNewProject(Project project) {
        if (projectDao.findByNameIgnoreCase(project.getName()).isPresent()) {
            throw new Error("Project already exists, please use a unique team name");
        } else {
            ProjectEntity newEntity = new ProjectEntity();
            newEntity.setName(project.getName());

            projectDao.save(newEntity);

            LOGGER.info("new project {} added", project.getName());
        }
    }

    public void updateProject(Project project) {
        Optional<ProjectEntity> entity = projectDao.findByNameIgnoreCase(project.getName());
        if (entity.isPresent()) {
            ProjectEntity updatedEntity = entity.get();
            // no values to update yet
            projectDao.save(updatedEntity);
        } else {
            throw new Error("Project does not exist");
        }
    }
}
