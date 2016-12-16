package com.nailonline.client;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;

/**
 * Created by Roman T. on 10.12.2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected UserTheme userTheme;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initTheme();

        setData(savedInstanceState);
    }

    protected void initTheme() {
        int userThemeId = PrefsHelper.getInstance().getUserThemeId(); //returns -1 if hasn't saved themeId
        userTheme = userThemeId >= 0 ? RealmHelper.getUserTheme(userThemeId) : RealmHelper.getDefaultTheme();
    }
    //to set themes

    protected void deepChangeTextColor() {
        if (toolbar != null) toolbar.setBackgroundColor(Color.parseColor("#" + userTheme.getThemeMC()));

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activityContent);
        deepChangeTextColor(viewGroup);
    }

    private void deepChangeTextColor(ViewGroup parentLayout) {
        for (int count = 0; count < parentLayout.getChildCount(); count++) {
            View view = parentLayout.getChildAt(count);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.parseColor("#" + userTheme.getThemeAC()));
            } else if (view instanceof ViewGroup) {
                deepChangeTextColor((ViewGroup) view);
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        deepChangeTextColor();
    }

    protected void setData(Bundle savedInstanceState){
        //nothing, can be overrided
    }

    protected abstract int getLayoutId();
}
