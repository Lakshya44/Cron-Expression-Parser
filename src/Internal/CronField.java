package Internal;

import java.util.*;
import java.util.stream.IntStream;

import CronFields.CronFieldType;

public class CronField {

    String fieldValue;
    CronFieldType cronFieldType;
    TreeSet<Integer> values;
    HashSet<Character> allowed_special_chars;

    public CronField(String fieldValue, CronFieldType cronFieldType) throws InvalidCronFieldException {

        values = new TreeSet<>();

        allowed_special_chars = new HashSet<>();

        allowed_special_chars.add('/');
        allowed_special_chars.add('*');
        allowed_special_chars.add(',');
        allowed_special_chars.add('-');

        this.cronFieldType = cronFieldType;
        this.fieldValue = fieldValue;

        isValidField();
        parseCommaSeperatedValues();
        parseHyphenSeperatedValues();
        parseAsterisk();

        if (values.isEmpty()) {
            int value = convertToInteger(fieldValue);
            if(cronFieldType.getMin() > value || cronFieldType.getMax() < value) {
                throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " is outside valid range (" + cronFieldType.getMin() + "-" + cronFieldType.getMax() + ")");
            }
            values.add(convertToInteger(fieldValue));
        }
    }

    void isValidField() throws InvalidCronFieldException {
        for(char ch: fieldValue.toCharArray()) {
            if(Character.isDigit(ch)) {
                continue;
            }
            if(allowed_special_chars.contains(ch) == false) {
                throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " contains invalid special character which is not part of " + allowed_special_chars);
            }
        }
    }
    
    void parseAsterisk() throws InvalidCronFieldException {
        if (fieldValue.startsWith("*")) {
            int interval = 1;
            String[] nums = fieldValue.split("/");
            if (nums.length > 2) {
                throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " are in wrong format");
            }
            if (nums.length == 2) {
                interval = convertToInteger(nums[1]);
            }
            addValues(cronFieldType.getMin(), cronFieldType.getMax(), interval);
        } else {
            parseSlashSeperatedValues();
        }
    }

    void parseSlashSeperatedValues() throws InvalidCronFieldException {
        if(!fieldValue.contains("/")) {
            return;
        }
        String[] nums = fieldValue.split("/");
        if(nums.length != 2) {
            throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " are in wrong format");
        }
        int start = convertToInteger(nums[0]);
        int end = cronFieldType.getMax();
        int interval = convertToInteger(nums[1]);
        addValues(start, end, interval);
    } 

    void parseHyphenSeperatedValues() throws InvalidCronFieldException {
        String[] nums = fieldValue.split("-");
        if (nums.length == 2) {
            int start = convertToInteger(nums[0]);
            int end = convertToInteger(nums[1]);
            addValues(start, end, 1);
        }
    }

    public TreeSet<Integer> getValues() {
        return values;
    }
 
    void parseCommaSeperatedValues() throws InvalidCronFieldException {
        String[] nums = fieldValue.split(",");
        if (nums.length > 1) {
            for (String num : nums) {
                int number = convertToInteger(num);
                addValues(number, number, 1);
            }
        }
    }

    void addValues(int start, int end, int steps) throws InvalidCronFieldException {

        if (start < cronFieldType.getMin() || end > cronFieldType.getMax()) {
            throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " is outside valid range (" + cronFieldType.getMin() + "-" + cronFieldType.getMax() + ")");
        }

        if (end < start) {
            throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " has invalid range");
        }
        
        if (steps == 0) {
            throw new InvalidCronFieldException("Field Value " + fieldValue + " for " + cronFieldType.getTypeName() + " steps are 0");
        }
        
        IntStream.rangeClosed(start, end)
        .filter(value -> (value - start) % steps == 0)
        .forEach(values::add);
       
    }

    Integer convertToInteger(String num) throws InvalidCronFieldException {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new InvalidCronFieldException("Invalid number '" + num + "' in field " + cronFieldType.getTypeName() + ": " + e.getMessage() + " cannot be converted into Integer");
        }
    }
}
