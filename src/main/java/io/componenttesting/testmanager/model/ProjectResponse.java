package io.componenttesting.testmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {

    private String name;

    private Rating rating;

    private List<TestData> testdata;
}
