package kloojj.com.autolinkmytest.autolinkutils;

/**
 * enum which contains supported types for linkify spans.
 */
public enum SpanMode {

    HASHTAG("Hashtag"), // For hash tagging
    MENTION("Mention"), // When mentioned a user
    EMAIL("Email"), // Spans Emails
    URL("Url"), // Spans URLs
    PHONE("Phone"), // Spans Phone number
    CUSTOM("Custom"); // Add custom regex, which want to handle

    private String name;

    SpanMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
