package CronFields;

public class DayOfMonthType implements CronFieldType {

    @Override
    public int getMin() {
        return 1;
    }

    @Override
    public int getMax() {
        return 31;
    }

    @Override
    public String getTypeName() {
        return "day of month";
    }
}
