package com.nailonline.client.master;

import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
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
import com.squareup.picasso.Picasso;

/**
 * Created by Roman T. on 21.12.2016.
 */

public class MasterListFragment extends BaseMasterListFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_master_list;
    }

    @Override
    protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return new MasterListAdapter();
    }

    private void onInfoButtonClick(View v){
        int masterId = (int)v.getTag();
        DialogMasterTbsInfoFragment fragment = new DialogMasterTbsInfoFragment();
        Bundle args = new Bundle();
        args.putInt(DialogMasterTbsInfoFragment.MASTER_ID_KEY, masterId);
        fragment.setArguments(args);
        fragment.show(getChildFragmentManager(), "");
    }

    protected class MasterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public MasterListFragment.ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_master_list_item, parent, false);
            view.findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        ((MasterTabActivity) getActivity()).makeNewOrder((int) view.getTag());
                    }
                }
            });
            return new MasterListFragment.ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            Master masterItem = masterList.get(position);
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + masterItem.getMasterPhoto())
                    .into(listViewHolder.photoView);
            listViewHolder.masterName.setText(masterItem.getMasterFirstName());
            MasterLocation masterLocation = masterItem.getMasterLocation();
            if (masterLocation != null && masterLocation.getAddress() != null) {
                listViewHolder.masterAddress.setText(masterLocation.getAddress());
                listViewHolder.masterAddress.setTextColor(((BaseActivity) getActivity()).getUserTheme().getParsedAC());
            }
            listViewHolder.infoButton.setColorFilter(((BaseActivity) getActivity()).getUserTheme().getParsedMC());
            listViewHolder.infoButton.setTag(masterItem.getMasterId());
            //painting list items
            if (position % 2 != 0) {
                int color = ColorUtils.setAlphaComponent(((BaseActivity) getActivity()).getUserTheme().getParsedMC(), 20);
                listViewHolder.rootView.setBackgroundColor(color);
            }
            ((ListViewHolder) holder).rootView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return masterList.size();
        }
    }

    protected class ListViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView photoView;
        TextView masterName;
        TextView masterAddress;
        ImageView infoButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            photoView = (ImageView) itemView.findViewById(R.id.master_photo);
            masterName = (TextView) itemView.findViewById(R.id.master_name);
            masterAddress = (TextView) itemView.findViewById(R.id.master_address);
            infoButton = (ImageView) itemView.findViewById(R.id.info_button);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onInfoButtonClick(view);
                }
            });
        }
    }
}
