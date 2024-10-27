package com.example.demo.factory;

public class GradingStrategyFactory{
    public static GradingStrategy getStrategy(String type){
        if(type.equalsIgnoreCase("letter")){
            return new LetterGradingStrategy();
        }
        else if(type.equalsIgnoreCase("percentage")){
            return new PercentageGradingStrategy();
        }
        return null;
    }
}