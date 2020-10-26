package io.componenttesting.teammorale.dao;

import io.componenttesting.teammorale.model.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsDao extends JpaRepository<TeamsEntity, Integer> {
}
