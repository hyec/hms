package project.hms.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDNumValidator implements ConstraintValidator<ValidIDNum, String> {

    private final static int[] CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private Pattern pattern;
    private DateFormat dateFormat;

    @Override
    public void initialize(ValidIDNum constraintAnnotation) {
        this.pattern = Pattern.compile("^(\\d{6})(\\d{8})\\d{3}([0-9xX])$");
        this.dateFormat = new SimpleDateFormat("YYYYMMDD");
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        if (value.length() != 18) {
            return false;
        }

        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            return false;
        }

        Date date;
        String dateStr = matcher.group(2);
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }

        if (date.after(new Date())) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            char ch = value.charAt(i);
            sum += (ch - '0') * CODE_WEIGHT[i];
        }

        Character s = VERIFY_CODE[sum % 11];

        if (!matcher.group(3).equals(s.toString())) {
            return false;
        }
        return true;
    }
}
