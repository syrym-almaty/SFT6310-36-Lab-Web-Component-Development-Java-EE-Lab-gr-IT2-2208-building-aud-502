public interface GradingStrategy {
    Double calculateGrade(Double score);
}

public class LetterGradingStrategy implements GradingStrategy {
    @Override
    public Double calculateGrade(Double score) {
        // Convert numeric score to letter grade
    }
}

public class GradingStrategyFactory {
    public static GradingStrategy getStrategy(String type) {
        if (type.equalsIgnoreCase("letter")) {
            return new LetterGradingStrategy();
        } else if (type.equalsIgnoreCase("percentage")) {
            return new PercentageGradingStrategy();
        }
        return null;
    }
}