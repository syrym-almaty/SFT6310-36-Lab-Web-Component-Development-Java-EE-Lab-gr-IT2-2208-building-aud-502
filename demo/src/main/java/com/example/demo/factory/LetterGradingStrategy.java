package com.example.demo.factory;

public class LetterGradingStrategy implements GradingStrategy{
    @Override
    public Double calculateGrade(Double score){
        if (score >= 90){
            return 4.0; //A
        }
        else if(score >= 80){
            return 3.0; //B
        }
        return 0.0; //F
    }
}
