package com.nailonline.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman T. on 16.12.2016.
 */

public class UserThemeActivity extends BaseActivity {

    private List<UserTheme> itemList;
    private View.OnClickListener onItemClickListener;
    private UserThemeAdapter adapter;
    private boolean themeChanged;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_theme;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = RealmHelper.getAllThemes();
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (itemList == null) itemList = new ArrayList<>();
        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tag - clicked position
                if (view.getTag() != null){
                    onThemeClicked(itemList.get((int)view.getTag()));
                }
            }
        };
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.themeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserThemeAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void onThemeClicked(UserTheme theme){
        if (!userTheme.getThemeId().equals(theme.getThemeId())){
            themeChanged = true;
            userTheme = theme;
            PrefsHelper.getInstance().setUserThemeId(userTheme.getThemeId());

            //to finish ripple animation
           new Handler(this.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    toolbar.setBackgroundColor(userTheme.getParsedMC());
                    adapter.notifyDataSetChanged();
                }
            }, 300);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (themeChanged) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private class UserThemeAdapter extends RecyclerView.Adapter<UserThemeAdapter.ThemeViewHolder> {
        @SuppressLint("InflateParams")
        @Override
        public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_theme_item, null);
            view.setOnClickListener(onItemClickListener);
            return new ThemeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ThemeViewHolder holder, int position) {
            UserTheme item = itemList.get(position);
            holder.title.setText(item.getThemeName());
            holder.rootView.setTag(position);
            holder.rootView.setBackgroundColor(item.getParsedMC());
            if (userTheme.getThemeId().equals(item.getThemeId()))
                holder.imageViewCheck.setVisibility(View.VISIBLE);
            else holder.imageViewCheck.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        class ThemeViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            View imageViewCheck;
            View rootView;

            ThemeViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.itemTitle);
                rootView = itemView.findViewById(R.id.itemLayout);
                imageViewCheck = itemView.findViewById(R.id.imageViewCheck);
            }
        }
    }
}
