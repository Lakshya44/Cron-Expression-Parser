package CronFields;

public class MinutesType implements CronFieldType {

    @Override
    public int getMin() {
        return 0;
    }

    @Override
    public int getMax() {
        return 59;
    }

    @Override
    public String getTypeName() {
        return "minute";
    }
}
