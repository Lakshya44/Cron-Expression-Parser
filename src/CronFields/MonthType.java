package CronFields;

public class MonthType implements CronFieldType {

    @Override
    public int getMin() {
        return 1;
    }

    @Override
    public int getMax() {
        return 12;
    }

    @Override
    public String getTypeName() {
        return "month";
    }
}
