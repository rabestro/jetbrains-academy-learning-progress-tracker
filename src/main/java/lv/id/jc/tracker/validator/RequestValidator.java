package lv.id.jc.tracker.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RequestValidator {
    CREDENTIALS("(?<FIRSTNAME>\\S+)\\s+(?<LASTNAME>.+?)\\s+(?<EMAIL>\\S+)",
            "Incorrect credentials"),
    POINTS("(?<ID>\\S+) (?<JAVA>\\d+) (?<DSA>\\d+) (?<DATABASES>\\d+) (?<SPRING>\\d+)",
            "Incorrect points format");

    private final Pattern validator;
    private final String errorMessage;

    RequestValidator(final String regex, final String errorMessage) {
        this.validator = Pattern.compile(regex);
        this.errorMessage = errorMessage;
    }

    public Matcher getMatcher(String request) {
        return validator.matcher(request);
    }

    public Optional<String> validate(Matcher matcher) {
        return matcher.matches() ? Optional.empty() : Optional.of(errorMessage);
    }
}