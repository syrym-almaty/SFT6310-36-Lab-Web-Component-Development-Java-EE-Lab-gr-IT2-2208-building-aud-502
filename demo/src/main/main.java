public class Main {
    public static void main(String[] args) {
        GradingService letterGradingService = new GradingService("letter");
        System.out.println("Letter Grade: " + letterGradingService.gradeStudent(85.0)); // 3.0 (B)

        GradingService percentageGradingService = new GradingService("percentage");
        System.out.println("Percentage Grade: " + percentageGradingService.gradeStudent(85.0)); // 85.0
    }
}
