package io.componenttesting.teammorale.dao;

import io.componenttesting.teammorale.model.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamsDao extends JpaRepository<TeamsEntity, Long> {

    Optional<TeamsEntity> findByNameIgnoreCase(String name);
}
