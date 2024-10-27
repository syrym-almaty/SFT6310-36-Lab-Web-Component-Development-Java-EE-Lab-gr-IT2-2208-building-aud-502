package com.example.demo.manager;

import com.example.demo.factory.GradingStrategy;
import com.example.demo.factory.GradingStrategyFactory;

public class GradingManager {
    private static GradingManager instance;

    private GradingManager() {
        // Initialization logic
    }

    public static synchronized GradingManager getInstance() {
        if (instance == null) {
            instance = new GradingManager();
        }
        return instance;
    }

    public Double applyStrategy(String strategyType, Double score) {
        GradingStrategy strategy = GradingStrategyFactory.getStrategy(strategyType);
        return strategy != null ? strategy.calculateGrade(score) : null;
    }
}
