package CronFields;

public class HoursType implements CronFieldType {

    @Override
    public int getMin() {
        return 0;
    }

    @Override
    public int getMax() {
        return 23;
    }

    @Override
    public String getTypeName() {
        return "hour";
    }
}
