package com.nailonline.client.master;

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

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class MasterGalleryFragment extends Fragment {

    private static final int WITHOUT_TBS = 1;
    private static final int WITH_TBS = 2;

    private List<Master> masterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View fragmentView = inflater.inflate(R.layout.fragment_master_gallery, container, false);
        initRecyclerView(fragmentView);
        masterList = ((MasterTabActivity) getActivity()).getMasterList();
        return fragmentView;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.master_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new GalleryAdapter());
    }

    private class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            Master master = masterList.get(position);
            if (master.hasTbs()) return WITH_TBS;
            else return WITHOUT_TBS;
        }

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view;
            if (viewType == WITHOUT_TBS) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_master_gallery_item, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_master_gallery_tbs_item, parent, false);
            }
            View submitView = view.findViewById(R.id.submit_layout);
            submitView.setBackgroundColor(((BaseActivity) getActivity()).getUserTheme().getParsedAC());
            submitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        ((MasterTabActivity) getActivity()).makeNewOrder((int) view.getTag());
                    }
                }
            });
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            GalleryViewHolder galleryHolder = (GalleryViewHolder) holder;
            Master masterItem = masterList.get(position);
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + masterItem.getMasterPhoto())
                    .into(galleryHolder.photoView);
            galleryHolder.masterName.setText(masterItem.getMasterFirstName());
            MasterLocation masterLocation = masterItem.getMasterLocation();
            if (masterLocation != null && masterLocation.getAddress() != null) {
                galleryHolder.masterAddress.setText(masterLocation.getAddress());
            } else galleryHolder.masterAddress.setText("");
            galleryHolder.submitView.setTag(position);

            if (holder.getItemViewType() == WITH_TBS) {
                ImageView tbs1View = (ImageView) ((GalleryViewHolder) holder).rootView.findViewById(R.id.image_tbs1);
                ImageView tbs2View = (ImageView) ((GalleryViewHolder) holder).rootView.findViewById(R.id.image_tbs2);
                ImageView tbs3View = (ImageView) ((GalleryViewHolder) holder).rootView.findViewById(R.id.image_tbs3);
                ImageView tbs4View = (ImageView) ((GalleryViewHolder) holder).rootView.findViewById(R.id.image_tbs4);

                if (masterItem.getMasterTbs1() != null) fillTbs(tbs1View, masterItem.getMasterTbs1());
                if (masterItem.getMasterTbs2() != null) fillTbs(tbs2View, masterItem.getMasterTbs2());
                if (masterItem.getMasterTbs3() != null) fillTbs(tbs3View, masterItem.getMasterTbs3());
                if (masterItem.getMasterTbs4() != null) fillTbs(tbs4View, masterItem.getMasterTbs4());
            }
        }

        private void fillTbs(ImageView tbsImageView, int skillId){
            Skill skill = RealmHelper.getSkillById(skillId);
            if (skill == null) return;
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_TBS + skill.getPhotoName())
                    .into(tbsImageView);
        }

        @Override
        public int getItemCount() {
            return masterList.size();
        }
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView photoView;
        TextView masterName;
        TextView masterAddress;
        View submitView;
        View rootView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            photoView = (ImageView) itemView.findViewById(R.id.master_image_view);
            masterName = (TextView) itemView.findViewById(R.id.master_name);
            masterAddress = (TextView) itemView.findViewById(R.id.master_address);
            submitView = itemView.findViewById(R.id.submit_layout);
            rootView = itemView.findViewById(R.id.root_view);
        }
    }
}
