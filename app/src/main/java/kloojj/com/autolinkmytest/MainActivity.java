package kloojj.com.autolinkmytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import kloojj.com.autolinkmytest.autolinkutils.AutoSpanTextView.OnSpanClickListener;
import kloojj.com.autolinkmytest.autolinkutils.SpanMode;
import kloojj.com.autolinkmytest.autolinkutils.AutoSpanTextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoSpanTextView autoSpanTextView = (AutoSpanTextView) findViewById(R.id.active);

        // autoLinkTextView.enableUnderLine();

        autoSpanTextView.addSpanMode(
                SpanMode.HASHTAG,
                SpanMode.PHONE,
                SpanMode.URL,
                SpanMode.EMAIL,
                SpanMode.MENTION);

        autoSpanTextView.setHashtagModeColor(ContextCompat.getColor(this, R.color.color2));
        autoSpanTextView.setMentionModeColor(ContextCompat.getColor(this, R.color.color5));

        autoSpanTextView.setText(getString(R.string.long_text));

        autoSpanTextView.setOnSpanClickListener(new OnSpanClickListener() {
            @Override
            public void onSpanTextClick(SpanMode spanMode, String spanText) {
                showDialog(spanText, "Mode is: " + spanMode.toString());
            }
        });
    }

    private void showDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
