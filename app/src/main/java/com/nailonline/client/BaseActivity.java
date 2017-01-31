package com.nailonline.client;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    protected boolean isNeedDeepSetColor = true;



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

    protected void changeColorsForTheme() {
        if (toolbar != null) {
            toolbar.setBackgroundColor(userTheme.getParsedMC());
            toolbar.setTitleTextColor(Color.WHITE);
        }
        if (isNeedDeepSetColor){
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activityContent);
            changeColorsForTheme(viewGroup);
        }
    }

    private void changeColorsForTheme(ViewGroup parentLayout) {
        if (parentLayout == null){
            Log.e("Nail", "activity content id not found");
            return;
        }
        for (int count = 0; count < parentLayout.getChildCount(); count++) {
            View view = parentLayout.getChildAt(count);
            if (view instanceof TextView && view.getTag() != null) {
                if (view.getTag().equals(getString(R.string.tag_painted_ac))) {
                    ((TextView)view).setTextColor(userTheme.getParsedAC());
                } else  if (view.getTag().equals(getString(R.string.tag_painted_mc))){
                    ((TextView)view).setTextColor(userTheme.getParsedMC());
                }
            } else if (view instanceof ImageView && view.getTag() != null) {
                if (view.getTag().equals(getString(R.string.tag_painted_ac))){
                    ((ImageView)view).setColorFilter(userTheme.getParsedAC());
                } else if (view.getTag().equals(getString(R.string.tag_painted_mc))){
                    ((ImageView)view).setColorFilter(userTheme.getParsedMC());
                }
            } else if (view instanceof ViewGroup) {
                changeColorsForTheme((ViewGroup) view);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public UserTheme getUserTheme(){
        return userTheme;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        changeColorsForTheme();
    }

    protected void setData(Bundle savedInstanceState){
        //nothing, can be overrided
    }

    protected abstract int getLayoutId();
}
