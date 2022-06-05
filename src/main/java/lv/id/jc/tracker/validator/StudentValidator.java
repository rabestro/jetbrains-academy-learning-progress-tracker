package lv.id.jc.tracker.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StudentValidator {
    FIRSTNAME("\\p{Alpha}([-'](?=\\p{Alpha})|\\p{Alpha})*\\p{Alpha}", "Incorrect first name."),
    LASTNAME("(\\p{Alpha}([-'](?=\\p{Alpha})|\\p{Alpha})*\\p{Alpha}\\s*)+", "Incorrect last name."),
    EMAIL("\\p{LD}+(\\.\\p{LD}+){0,255}@(\\p{LD}+\\.){1,10}\\p{LD}+", "Incorrect email.");

    final String errorMessage;
    final Pattern validator;

    StudentValidator(final String regex, final String errorMessage) {
        this.validator = Pattern.compile(regex);
        this.errorMessage = errorMessage;
    }

    public Optional<String> validate(Matcher matcher) {
        var value = matcher.group(this.name());
        return validator.matcher(value).matches()
                ? Optional.empty()
                : Optional.of(errorMessage);
    }
}