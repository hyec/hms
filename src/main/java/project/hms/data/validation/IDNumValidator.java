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

    private Pattern pattern;
    private DateFormat dateFormat;

    @Override
    public void initialize(ValidIDNum constraintAnnotation) {
        this.pattern = Pattern.compile("^(\\d{6})(\\d{8})\\d{3}([0-9xX])$");
        this.dateFormat = new SimpleDateFormat("YYYYMMDD");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

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


        return true;
    }
}
