package io.componenttesting.teammorale.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface TeamMoraleSource {

    String APPLICATIONS_OUT = "output";

    @Output(APPLICATIONS_OUT)
    MessageChannel output();
}
