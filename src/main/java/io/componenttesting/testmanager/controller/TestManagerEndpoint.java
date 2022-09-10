package io.componenttesting.testmanager.controller;

import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.dao.ProjectEntity;
import io.componenttesting.api.ProjectsApi;
import io.componenttesting.model.ProjectCreate;
import io.componenttesting.model.ProjectResponse;
import io.componenttesting.testmanager.service.ProjectService;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TestManagerEndpoint implements ProjectsApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestManagerEndpoint.class);

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectService projectService;

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<String> handleException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @Override
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String projectName) {
        return ResponseEntity.ok().body(projectService.getProject(projectName));
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        return ResponseEntity.ok().body(projectService.getAllProjects());
    }

    @Override
    public ResponseEntity<Void> createProjects(@RequestBody @Valid ProjectCreate project) {
        try {
            projectService.createNewProject(project);
        } catch (Error e) {
            throw new InvalidArgumentException();
        }
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProject(String projectName) {
        Optional<ProjectEntity> result = projectDao.findByNameIgnoreCase(projectName);
        if (result.isPresent()) {
            projectDao.delete(result.get());
            LOGGER.info("project {} was deleted", projectName);
        }
        return null;
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public class InvalidArgumentException extends RuntimeException {
    }
}
