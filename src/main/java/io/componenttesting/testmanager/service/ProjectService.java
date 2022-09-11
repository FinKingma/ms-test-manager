package io.componenttesting.testmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.model.*;
import io.componenttesting.testmanager.dao.MetricsRepository;
import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.event.EventPublisher;
import io.componenttesting.testmanager.dao.ProjectEntity;
import io.componenttesting.testmanager.model.AverageTestResults;
import io.componenttesting.testmanager.dao.TestDataEntity;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private MetricsRepository metricsRepository;

    @Value("${metrics.tolerance}")
    private int metricTolerance;

    private final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @SneakyThrows
    public ProjectResponse getProject(String projectName) {
        Optional<ProjectEntity> result = projectDao.findByNameIgnoreCase(projectName);
        if (result.isPresent()) {
            AverageTestResults averageTestResults = metricsRepository.getAverageTestPassing();
            ProjectResponse project = map(result.get());
            return calculateProjectRating(project, averageTestResults);
        } else {
            throw new NotFoundException("project " + projectName + " was not found.");
        }
    }

    public List<ProjectResponse> getAllProjects() {
        List<ProjectEntity> result = projectDao.findAll();
        AverageTestResults averageTestResults = metricsRepository.getAverageTestPassing();
        return result.stream().map(project -> calculateProjectRating(map(project), averageTestResults)).collect(Collectors.toList());
    }



    private ProjectResponse calculateProjectRating(ProjectResponse projectResponse, AverageTestResults averageTestResults) {
        if (projectResponse.getTestdata().isEmpty()) {
            return projectResponse;
        }
        long passedTests = projectResponse.getTestdata().stream().filter(testData -> "PASSED".equalsIgnoreCase(testData.getResult())).count();
        int passedPercentage = Long.valueOf((passedTests * 100) / projectResponse.getTestdata().size()).intValue();
        Rating rating =
                passedPercentage >= averageTestResults.getAveragePassingPercentage() ? Rating.GOOD :
                passedPercentage + metricTolerance >= averageTestResults.getAveragePassingPercentage() ? Rating.AVERAGE :
                Rating.POOR;
        projectResponse.setRating(rating);

        return projectResponse;
    }

    private ProjectResponse map(ProjectEntity entity) {
        ProjectResponse project = new ProjectResponse();
        project.setName(entity.getName());
        project.setTestdata(entity.getTestdata().stream().map((td) -> {
            TestData testData = new TestData();
            testData.setResult(td.getResult());
            testData.setTestrunId(td.getTestrunId());
            testData.setTestname(td.getTestname());
            return testData;
        }).collect(Collectors.toList()));
        return project;
    }

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
        LOGGER.info("updating project {}", event.getProject());
        TestDataEntity testdata = new TestDataEntity();
        testdata.setTestname(event.getTestName());
        testdata.setTestrunId(event.getTestRunId());
        testdata.setResult(event.getResult());
        testdata.setProject(entity);

        entity.getTestdata().add(testdata);
        projectDao.save(entity);
    }

    public void createNewProject(ProjectCreate project) {
        if (projectDao.findByNameIgnoreCase(project.getName()).isPresent()) {
            throw new Error("Project already exists, please use a unique team name");
        } else {
            ProjectEntity newEntity = new ProjectEntity();
            newEntity.setName(project.getName());

            projectDao.save(newEntity);

            LOGGER.info("new project {} added", project.getName());
        }
    }
}
