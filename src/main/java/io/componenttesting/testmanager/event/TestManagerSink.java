package io.componenttesting.testmanager.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TestManagerSink {

    String APPLICATIONS_IN = "input";
    String APPROVED_OUT = "approved";
    String DECLINED_OUT = "declined";

    @Input(APPLICATIONS_IN)
    SubscribableChannel sourceOfTeamMorale();

    @Output(APPROVED_OUT)
    MessageChannel approved();

    @Output(DECLINED_OUT)
    MessageChannel declined();
}
