package io.componenttesting.testmanager.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface TestManagerSource {

    String APPLICATIONS_OUT = "output";

    @Output(APPLICATIONS_OUT)
    MessageChannel output();
}
