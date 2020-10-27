package io.componenttesting.teammorale.endpoint;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.model.TeamsEntity;
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
        when(dao.findByNameIgnoreCase("Fin")).thenReturn(Optional.of(t));

        TeamsEntity team = teamMoraleEndpoint.getByName("Fin");
        assertEquals("Fin", team.getName());
    }
}
