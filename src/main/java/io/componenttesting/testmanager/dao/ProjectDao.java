package io.componenttesting.testmanager.dao;

import io.componenttesting.testmanager.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectDao extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByNameIgnoreCase(String name);
}
