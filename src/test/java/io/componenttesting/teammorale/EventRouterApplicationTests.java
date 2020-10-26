package io.componenttesting.teammorale;

import io.componenttesting.teammorale.config.ApplicationConfig;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationConfig.class)
public class EventRouterApplicationTests {

    @Test
    public void contextLoads() {}

}
