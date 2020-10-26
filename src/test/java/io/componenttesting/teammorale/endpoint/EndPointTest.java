package io.componenttesting.teammorale.endpoint;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.endpoint.TeamMoraleEndpoint;
import io.componenttesting.teammorale.model.TeamsEntity;
import io.componenttesting.teammorale.vo.Team;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EndPointTest {

    @InjectMocks
    private TeamMoraleEndpoint teamMoraleEndpoint;

    @Mock
    private TeamsDao dao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTeamData() {
        TeamsEntity t = new TeamsEntity();
        t.setName("Fin");
        when(dao.findById(1)).thenReturn(Optional.of(t));

        Team team = teamMoraleEndpoint.getById(1);
        assertEquals("Fin", team.getName());
    }
}
