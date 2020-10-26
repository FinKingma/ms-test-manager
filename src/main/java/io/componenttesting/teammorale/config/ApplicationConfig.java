package io.componenttesting.teammorale.config;

import io.componenttesting.teammorale.event.EventPublisher;
import io.componenttesting.teammorale.event.EventReceiver;
import io.componenttesting.teammorale.event.TeamMoraleSink;
import io.componenttesting.teammorale.event.TeamMoraleSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class ApplicationConfig implements WebFluxConfigurer {


}
