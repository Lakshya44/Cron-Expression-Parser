package Internal;

import java.util.*;
import java.util.stream.Collectors;

import CronFields.*;

public class CronExpression {

    CronField minutes, hours, month, day_of_week, day_of_month;
    String command;

    public CronExpression(String cron_expression_input) throws InvalidCronFieldException {
        List<String> cron_members = Arrays.asList(cron_expression_input.split("\\s+"));
        if (cron_members.size() != 6) {
            throw new InvalidCronFieldException("Invalid cron Expression! Must contain 6 fields but got " 
                        + cron_members.size() + " fields in " 
                        + cron_expression_input);
        }
        minutes = new CronField(cron_members.get(0), new MinutesType());
        hours = new CronField(cron_members.get(1), new HoursType());
        day_of_month = new CronField(cron_members.get(2), new DayOfMonthType());
        month = new CronField(cron_members.get(3), new MonthType());
        day_of_week = new CronField(cron_members.get(4), new DayOfWeekType());
        command = cron_members.get(5);
    }

    public String convertToString(TreeSet<Integer> values) {
        return values.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public void printResult() {
        System.out.println("minute " + convertToString(minutes.values));
        System.out.println("hour " + convertToString(hours.values));
        System.out.println("day of month " + convertToString(day_of_month.values));
        System.out.println("month " + convertToString(month.values));
        System.out.println("day of week " + convertToString(day_of_week.values));
        System.out.println("command " + command);
    }
}
