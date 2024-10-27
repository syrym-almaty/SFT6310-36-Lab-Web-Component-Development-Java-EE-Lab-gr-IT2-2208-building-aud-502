package com.example.demo.factory;

public class PercentageGradingStrategy implements GradingStrategy {
    @Override
    public Double calculateGrade(Double score) {
        // Assume score is already in percentage format
        return score;
    }
}