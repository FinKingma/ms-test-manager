package io.componenttesting.testmanager.controller;

import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.dao.ProjectEntity;
import io.componenttesting.testmanager.model.ProjectResponse;
import io.componenttesting.testmanager.service.ProjectService;
import io.componenttesting.testmanager.model.Project;
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
@RequestMapping("api/v1/")
public class TestManagerEndpoint {

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

    @GetMapping("/{name}")
    public ResponseEntity<ProjectResponse> getByName(@PathVariable String name) throws NotFoundException {
        return ResponseEntity.ok().body(projectService.getProject(name));
    }

    @GetMapping()
    public ResponseEntity<List<ProjectResponse>> getAll() {
        return ResponseEntity.ok().body(projectService.getAllProjects());
    }

    @PostMapping()
    public void createNewProject(@RequestBody @Valid Project project) {
        try {
            projectService.createNewProject(project);
        } catch (Error e) {
            throw new InvalidArgumentException();
        }
    }

    @PutMapping()
    public void updateProject(@RequestBody @Valid Project project) {
        try {
            projectService.updateProject(project);
        } catch (Error e) {
            throw new InvalidArgumentException();
        }
    }

    @DeleteMapping("/{name}")
    public void deleteProject(@PathVariable String name) {
        Optional<ProjectEntity> result = projectDao.findByNameIgnoreCase(name);
        if (result.isPresent()) {
            projectDao.delete(result.get());
            LOGGER.info("project {} was deleted", name);
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public class InvalidArgumentException extends RuntimeException {
    }
}
