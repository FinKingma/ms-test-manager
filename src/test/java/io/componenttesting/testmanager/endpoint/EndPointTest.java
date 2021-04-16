package io.componenttesting.testmanager.endpoint;

import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.model.ProjectEntity;
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
    private TestManagerEndpoint teamMoraleEndpoint;

    @Mock
    private ProjectDao projectDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProjectData() {
        ProjectEntity p = new ProjectEntity();
        p.setName("Fin");
        when(projectDao.findByNameIgnoreCase("Fin")).thenReturn(Optional.of(p));

        ProjectEntity team = teamMoraleEndpoint.getByName("Fin");
        assertEquals("Fin", team.getName());
    }
}
