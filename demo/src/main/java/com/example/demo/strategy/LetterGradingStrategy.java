package com.example.demo.strategy;

public class LetterGradingStrategy implements GradingStrategy {
    @Override
    public Double calculateGrade(Double score) {
        if (score >= 90) return 4.0;  // A
        else if (score >= 80) return 3.0;  // B
        else if (score >= 70) return 2.0;  // C
        else if (score >= 60) return 1.0;  // D
        else return 0.0;  // F
    }
}
