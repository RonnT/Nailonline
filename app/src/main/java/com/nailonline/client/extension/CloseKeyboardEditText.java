package com.nailonline.client.extension;


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class CloseKeyboardEditText extends EditText {
    protected View focusLayout;

    public void setFocusLayout(View focusLayout) {
        this.focusLayout = focusLayout;
    }

    public CloseKeyboardEditText(Context context) {
        super(context);
    }

    public CloseKeyboardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CloseKeyboardEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if(focusLayout != null)
                focusLayout.requestFocus();
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}