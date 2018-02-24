package kloojj.com.autolinkmytest.autolinkutils;

import android.util.Log;
import android.util.Patterns;
import java.util.regex.Pattern;

/**
 * Utility class which provides utility methods for Spanning a text.
 */
class SpanUtils {

    static final String PHONE_REGEX = Patterns.PHONE.pattern();
    static final String EMAIL_REGEX = Patterns.EMAIL_ADDRESS.pattern();
    static final String HASHTAG_REGEX = "(?:^|\\s|$)#[\\p{L}0-9_]*";
    static final String MENTION_REGEX = "(?:^|\\s|$|[.])@[\\p{L}0-9_]*";
    static final String URL_REGEX = "(^|[\\s.:;?\\-\\]<\\(])" +
            "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#](\\(\\))?)" +
            "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";


    static final Pattern PHONE_PATTERN = Patterns.PHONE;
    static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
    static final Pattern HASHTAG_PATTERN = Pattern.compile("(?:^|\\s|$)#[\\p{L}0-9_]*");
    static final Pattern MENTION_PATTERN = Pattern.compile("(?:^|\\s|$|[.])@[\\p{L}0-9_]*");
    static final Pattern URL_PATTERN = Pattern.compile("(^|[\\s.:;?\\-\\]<\\(])" +
            "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#](\\(\\))?)" +
            "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])");


    private static boolean isValidRegex(String regex) {
        return regex != null && !regex.isEmpty() && regex.length() > 2;
    }

    static Pattern getPattern(SpanMode spanMode, String customRegex) {
        switch (spanMode) {
            case HASHTAG:
                return HASHTAG_PATTERN;
            case MENTION:
                return MENTION_PATTERN;
            case URL:
                return URL_PATTERN;
            case PHONE:
                return PHONE_PATTERN;
            case EMAIL:
                return EMAIL_PATTERN;
            case CUSTOM:
                if (!SpanUtils.isValidRegex(customRegex)) {
                    Log.e(AutoSpanTextView.TAG, "Your custom regex is null, returning HASHTAG_PATTERN");
                    return HASHTAG_PATTERN;
                } else {
                    return Pattern.compile(customRegex);
                }
            default:
                // Hash tag is default for kloojj (as per uses)
                return HASHTAG_PATTERN;
        }
    }

    static String getRegex(SpanMode spanMode, String customRegex) {
        switch (spanMode) {
            case HASHTAG:
                return HASHTAG_REGEX;
            case MENTION:
                return MENTION_REGEX;
            case URL:
                return URL_REGEX;
            case PHONE:
                return PHONE_REGEX;
            case EMAIL:
                return EMAIL_REGEX;
            case CUSTOM:
                if (!SpanUtils.isValidRegex(customRegex)) {
                    Log.e(AutoSpanTextView.TAG, "Your custom regex is null, returning HASHTAG_REGEX");
                    return HASHTAG_REGEX;
                } else {
                    return customRegex;
                }
            default:
                // Hash tag is default for kloojj (as per uses)
                return HASHTAG_REGEX;
        }
    }
}
