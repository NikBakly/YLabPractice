package secondWeek.TaskSnilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        boolean result = false;
        if (checkRegex(snils)) {
            StringBuilder newSnils = new StringBuilder();
            for (char value : snils.toCharArray()) {
                if (value >= '0' && value <= '9') {
                    newSnils.append(value);
                }
            }
            int sum = getSumSnils(newSnils.toString());
            int controlNumberFromSumSnils = getControlNumberFromSumSnils(sum);
            int controlNumberFromSnils = getControlNumberFromSnils(newSnils.toString());
            result = controlNumberFromSumSnils == controlNumberFromSnils;
        }

        return result;
    }

    private boolean checkRegex(String snils) {
        String regexWithThreeSeparators = "^\\d{3}-\\d{3}-\\d{3}-\\d{2}$";
        String regexWithTwoSeparators = "^\\d{3}-\\d{3}-\\d{3} \\d{2}$";
        String regexWithoutSeparators = "^\\d{11}$";
        return snils.matches(regexWithThreeSeparators)
                || snils.matches(regexWithTwoSeparators)
                || snils.matches(regexWithoutSeparators);
    }

    private int getSumSnils(String snils) {
        int sum = 0;
        int ratio = 9;
        for (int i = 0; i < 9; i++) {
            int value = Integer.parseInt(String.valueOf(snils.charAt(i)));
            sum += ratio * value;
            --ratio;
        }
        return sum;
    }

    private int getControlNumberFromSumSnils(int sum) {
        int controlNumberFromSumSnils;
        if (sum < 100) {
            controlNumberFromSumSnils = sum;
        } else if (sum == 100 || sum % 101 == 0) {
            controlNumberFromSumSnils = 0;
        } else {
            controlNumberFromSumSnils = sum % 101;
        }
        return controlNumberFromSumSnils;
    }

    private int getControlNumberFromSnils(String snils) {
        int result;
        if (snils.charAt(9) == '0') {
            result = Integer.parseInt(
                    String.format("%s", snils.charAt(10))
            );
        } else {
            result = Integer.parseInt(
                    String.format("%s%s", snils.charAt(10), snils.charAt(11))
            );
        }
        return result;
    }
}
