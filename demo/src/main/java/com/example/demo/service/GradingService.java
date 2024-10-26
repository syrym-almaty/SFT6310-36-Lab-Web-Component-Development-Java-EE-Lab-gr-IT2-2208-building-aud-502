package com.example.demo.service;

public class GradingService {
    private GradingStrategy gradingStrategy;

    public GradingService(String strategyType) {
        this.gradingStrategy = GradingStrategyFactory.getStrategy(strategyType);
    }

    public Double gradeStudent(Double score) {
        return gradingStrategy.calculateGrade(score);
    }
}
