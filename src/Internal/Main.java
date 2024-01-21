package Internal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the cron expression: ");
            String cron_expression_input = sc.nextLine();
            sc.close();
            CronExpression cronExpression = new CronExpression(cron_expression_input.trim());
            cronExpression.printResult();
        } catch (InvalidCronFieldException e) {
            System.err.println(e.getMessage());
        }
    }
}
