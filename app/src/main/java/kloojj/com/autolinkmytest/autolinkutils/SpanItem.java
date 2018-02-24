package kloojj.com.autolinkmytest.autolinkutils;

/**
 * Span info which need to be colored and handle onclick.
 */
class SpanItem {

    private SpanMode mSpanMode;

    private String matchedText;

    private int startPos, endPos;

    SpanItem(int startPos, int endPos, String matchedText, SpanMode spanMode) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.matchedText = matchedText;
        this.mSpanMode = spanMode;
    }

    SpanMode getSpanMode() {
        return mSpanMode;
    }

    String getMatchedText() {
        return matchedText;
    }

    int getStartPos() {
        return startPos;
    }

    int getEndPos() {
        return endPos;
    }
}
