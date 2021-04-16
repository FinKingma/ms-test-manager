package io.componenttesting.testmanager.dao;

import io.componenttesting.testmanager.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ProjectDao extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByNameIgnoreCase(String name);
}
