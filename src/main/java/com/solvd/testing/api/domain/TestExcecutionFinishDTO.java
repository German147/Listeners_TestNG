package com.solvd.testing.api.domain;

public class TestExcecutionFinishDTO {
    private String result;
    private String endedAt;

    public TestExcecutionFinishDTO(String result, String endedAt) {
        this.result = result;
        this.endedAt = endedAt;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getEndedAt() {
        return endedAt;
    }
    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }
}
