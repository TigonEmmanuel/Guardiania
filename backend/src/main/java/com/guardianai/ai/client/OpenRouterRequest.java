package com.guardianai.ai.client;

public class OpenRouterRequest {
    private String model;
    private String input;

    public OpenRouterRequest() {}

    public OpenRouterRequest(String model, String input) {
        this.model = model;
        this.input = input;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
}
