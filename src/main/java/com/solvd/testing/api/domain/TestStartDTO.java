package com.solvd.testing.api.domain;

import com.solvd.testing.api.utils.DateFormatter;

public class TestStartDTO {
    private String name;
    private String framework;
    private String startedAt;

    public TestStartDTO() {
    }

    public TestStartDTO(String name, String framework) {
        this.name = name;
        this.framework = framework;
        this.startedAt = DateFormatter.getCurrentTime();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }
}
