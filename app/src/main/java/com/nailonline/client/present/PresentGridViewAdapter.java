package com.nailonline.client.present;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Present;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roman T. on 29.01.2017.
 */

public class PresentGridViewAdapter extends BaseAdapter {

    private List<Present> itemList;
    private Context context;


    PresentGridViewAdapter(Context context, List<Present> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View grid;
        Present present = (Present) getItem(i);

        if (convertView == null){
            grid = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_grid_present, viewGroup, false);
        } else grid = convertView;

        ImageView presentImageView = (ImageView) grid.findViewById(R.id.presentImage);

        ((TextView)grid.findViewById(R.id.presentLabel)).setText(present.getLabel());
        Picasso.with(context)
                .load(BuildConfig.SERVER_IMAGE_PRESENTS + present.getPhotoName())
                .into(presentImageView);
        return grid;
    }
}
