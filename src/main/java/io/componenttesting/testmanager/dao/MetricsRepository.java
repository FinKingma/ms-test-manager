package io.componenttesting.testmanager.dao;

import io.componenttesting.testmanager.model.AverageTestResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class MetricsRepository {
    @Value("${metrics.endpoint}")
    private String metricsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public AverageTestResults getAverageTestPassing() {
        return restTemplate.getForObject(metricsEndpoint + "/average-passing-tests", AverageTestResults.class);
    }
}
