package io.componenttesting.testmanager.endpoint;

import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.model.ProjectEntity;
import io.componenttesting.testmanager.service.ProjectService;
import io.componenttesting.testmanager.vo.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class TeamMoraleEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMoraleEndpoint.class);

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectService projectService;

    @GetMapping("/{name}")
    public ProjectEntity getByName(@PathVariable String name) {
        Optional<ProjectEntity> result = projectDao.findByNameIgnoreCase(name);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new Error("did not find project " + name);
        }
    }

    @GetMapping()
    public List<ProjectEntity> getAll() {
        return projectDao.findAll();
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
