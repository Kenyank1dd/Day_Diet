package com.example.daydiet.model.entity;



public class ChatRequest {
    public String prompt;
    public String[][] history;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String[][] getHistory() {
        return history;
    }

    public void setHistory(String[][] history) {
        this.history = history;
    }

    public ChatRequest(String prompt, String[][] history) {
        this.prompt = prompt;
        this.history = history;
    }
}
