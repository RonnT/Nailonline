package com.nailonline.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nailonline.client.entity.Master;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class MasterGalleryFragment extends Fragment {

    private List<Master> masterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View fragmentView = inflater.inflate(R.layout.fragment_master_gallery, container, false);
        initRecyclerView(fragmentView);
        masterList = ((MasterTabActivity)getActivity()).getMasterList();
        return fragmentView;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new GalleryAdapter());
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder>{

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_master_gallery_item, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GalleryViewHolder holder, int position) {
            Master masterItem = masterList.get(position);
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + masterItem.getMasterPhoto())
                    .into(holder.photoView);
            holder.masterName.setText(masterItem.getMasterFirstName());
            holder.submitView.setBackgroundColor(((BaseActivity)getActivity()).getUserTheme().getParcedAC());
        }

        @Override
        public int getItemCount() {
            return masterList.size();
        }
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder{

        ImageView photoView;
        TextView masterName;
        TextView masterAddress;
        View submitView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            photoView = (ImageView) itemView.findViewById(R.id.master_image_view);
            masterName = (TextView) itemView.findViewById(R.id.master_name);
            masterAddress = (TextView) itemView.findViewById(R.id.master_address);
            submitView = itemView.findViewById(R.id.submit_layout);
        }
    }
}
