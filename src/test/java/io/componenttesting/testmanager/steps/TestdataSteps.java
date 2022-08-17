package io.componenttesting.testmanager.steps;

import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.dao.ProjectEntity;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class TestdataSteps {

    @Autowired
    ProjectDao projectDao;

    @Given("project {string} exists")
    public void createProject(String name) {
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        projectDao.save(project);
    }

}
