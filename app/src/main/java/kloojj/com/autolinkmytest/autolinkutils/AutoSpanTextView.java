package kloojj.com.autolinkmytest.autolinkutils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom {@link AppCompatTextView} which enable links for given {@link SpanMode}.
 *
 * <pre>
 *     <code>
 *     AutoSpanTextView autoSpanTv = (AutoSpanTextView) findViewById(R.id.auto_span_text_view);
 *
 *     // autoSpanTv.enableUnderLine();
 *
 *      autoSpanTv.addSpanMode(
 *      SpanMode.HASHTAG,
 *      SpanMode.MENTION,
 *      SpanMode.EMAIL,
 *      SpanMode.PHONE,
 *      SpanMode.URL
 *      );
 *
 *      autoSpanTv.setHashtagModeColor(ContextCompat.getColor(this, R.color.hashtag));
 *      autoSpanTv.setMentionModeColor(ContextCompat.getColor(this, R.color.mention));
 *      autoSpanTv.setPhoneModeColor(ContextCompat.getColor(this, R.color.phone));
 *      autoSpanTv.setCustomModeColor(ContextCompat.getColor(this, R.color.custom_span));
 *
 *      autoSpanTv.setText(getString(R.string.a_long_text_to_spanned));
 *
 *      autoSpanTv.setOnSpanClickListener(new OnSpanClickListener() {
 *              @Override
 *              public void onSpanTextClick(SpanMode spanMode, String matchedText) {
 *                   // Handle listener
 *              }
 *       });
 *     </code>
 * </pre>
 */
public class AutoSpanTextView extends AppCompatTextView {

    static final String TAG = AutoSpanTextView.class.getSimpleName();

    private static final int MIN_PHONE_NUMBER_LENGTH = 8;
    private static final int DEFAULT_COLOR = Color.BLUE;
    private OnSpanClickListener mOnSpanClickListener;
    private SpanMode[] mSpanModes;
    private String customRegex;
    private boolean isUnderLineEnabled = false;

    private int mentionModeColor = DEFAULT_COLOR;
    private int hashtagModeColor = DEFAULT_COLOR;
    private int urlModeColor = DEFAULT_COLOR;
    private int phoneModeColor = DEFAULT_COLOR;
    private int emailModeColor = DEFAULT_COLOR;
    private int customModeColor = DEFAULT_COLOR;
    private int defaultSelectedColor = Color.LTGRAY;

    /**
     * Onclick listener for Spans
     */
    public interface OnSpanClickListener {

        void onSpanTextClick(SpanMode spanMode, String spanText);
    }

    public AutoSpanTextView(Context context) {
        super(context);
    }

    public AutoSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setHighlightColor(int color) {
        super.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
            return;
        }
        SpannableString spannableString = makeSpannableString(text);
        setMovementMethod(new SpanTouchMovementMethod());
        super.setText(spannableString, type);
    }

    private SpannableString makeSpannableString(CharSequence text) {

        final SpannableString spannableString = new SpannableString(text);

        long startTime = System.nanoTime();
        Log.e("PANKAJ", "Start of finidng pills : " + startTime);

        List<SpanItem> spanItems = matchedRanges(text);

        long endTime = System.nanoTime();
        Log.e("PANKAJ", "End of finidng pills : " + endTime);
        long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.

        Log.e("PANKAJ", "Time Took MS : " + duration);

        for (final SpanItem spanItem : spanItems) {
            int currentColor = getColorByMode(spanItem.getSpanMode());

            TouchableSpan clickableSpan = new TouchableSpan(currentColor, defaultSelectedColor, isUnderLineEnabled) {
                @Override
                public void onClick(View widget) {
                    if (mOnSpanClickListener != null) {
                        mOnSpanClickListener.onSpanTextClick(
                                spanItem.getSpanMode(),
                                spanItem.getMatchedText());
                    }
                }
            };

            spannableString.setSpan(
                    clickableSpan,
                    spanItem.getStartPos(),
                    spanItem.getEndPos(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    private List<SpanItem> matchedRanges(CharSequence text) {

        List<SpanItem> spanItems = new LinkedList<>();

        if (mSpanModes == null) {
            throw new NullPointerException("Please add at least one Span mode");
        }

        for (SpanMode anSpanMode : mSpanModes) {
            /*String regex = SpanUtils.getRegex(anSpanMode, customRegex);
            Pattern pattern = SpanUtils.getRegex(anSpanMode, customRegex);*/
            Pattern pattern = SpanUtils.getPattern(anSpanMode, customRegex);
            Matcher matcher = pattern.matcher(text);

            if (anSpanMode == SpanMode.PHONE) {
                while (matcher.find()) {
                    if (matcher.group().length() > MIN_PHONE_NUMBER_LENGTH) {
                        spanItems.add(new SpanItem(
                                matcher.start(),
                                matcher.end(),
                                matcher.group(),
                                anSpanMode));
                    }
                }
            } else {
                while (matcher.find()) {
                    spanItems.add(new SpanItem(
                            matcher.start(),
                            matcher.end(),
                            matcher.group(),
                            anSpanMode));
                }
            }
        }

        return spanItems;
    }

    private int getColorByMode(SpanMode spanMode) {
        switch (spanMode) {
            case HASHTAG:
                return hashtagModeColor;
            case MENTION:
                return mentionModeColor;
            /*case URL:
                return urlModeColor;
            case PHONE:
                return phoneModeColor;
            case EMAIL:
                return emailModeColor;
            case CUSTOM:
                return customModeColor;*/
            default:
                return hashtagModeColor;
        }
    }

    public void setMentionModeColor(@ColorInt int mentionModeColor) {
        this.mentionModeColor = mentionModeColor;
    }

    public void setHashtagModeColor(@ColorInt int hashtagModeColor) {
        this.hashtagModeColor = hashtagModeColor;
    }

    public void setUrlModeColor(@ColorInt int urlModeColor) {
        this.urlModeColor = urlModeColor;
    }

    public void setPhoneModeColor(@ColorInt int phoneModeColor) {
        this.phoneModeColor = phoneModeColor;
    }

    public void setEmailModeColor(@ColorInt int emailModeColor) {
        this.emailModeColor = emailModeColor;
    }

    public void setCustomModeColor(@ColorInt int customModeColor) {
        this.customModeColor = customModeColor;
    }

    public void setSelectedStateColor(@ColorInt int defaultSelectedColor) {
        this.defaultSelectedColor = defaultSelectedColor;
    }

    public void addSpanMode(SpanMode... spanModes) {
        this.mSpanModes = spanModes;
    }

    public void setCustomRegex(String regex) {
        this.customRegex = regex;
    }

    public void setOnSpanClickListener(OnSpanClickListener onSpanClickListener) {
        this.mOnSpanClickListener = onSpanClickListener;
    }

    public void enableUnderLine() {
        isUnderLineEnabled = true;
    }
}
