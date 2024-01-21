package CronFields;

public class DayOfWeekType implements CronFieldType {

    @Override
    public int getMin() {
        return 1;
    }

    @Override
    public int getMax() {
        return 7;
    }

    @Override
    public String getTypeName() {
        return "day of week";
    }
}
