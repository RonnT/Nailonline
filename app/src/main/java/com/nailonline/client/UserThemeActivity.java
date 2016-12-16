package com.nailonline.client;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.helper.RealmHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman T. on 16.12.2016.
 */

public class UserThemeActivity extends BaseActivity {

    private List<UserTheme> itemList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_theme;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);

        itemList = RealmHelper.getAllThemes();
        Log.d("Nail", String.valueOf(itemList.size()));
        initRecyclerView();
    }

    private void initRecyclerView(){
        if (itemList == null) itemList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.themeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserThemeAdapter());
    }

    private class UserThemeAdapter extends RecyclerView.Adapter<UserThemeAdapter.ThemeViewHolder> {
        @SuppressLint("InflateParams")
        @Override
        public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_theme_item, null);
            return new ThemeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ThemeViewHolder holder, int position) {
            UserTheme item = itemList.get(position);
            holder.title.setText(item.getThemeName());
            holder.title.setBackgroundColor(Color.parseColor("#" + item.getThemeMC()));
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        class ThemeViewHolder extends RecyclerView.ViewHolder{

            TextView title;
            View rootView;

            ThemeViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.itemTitle);
                rootView = itemView.findViewById(R.id.itemLayout);
            }
        }
    }
}
