package com.nailonline.client.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Job;
import com.nailonline.client.entity.Master;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.master.DialogMasterTbsInfoFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class OrderByMasterFragment extends Fragment {

    private ArrayList<Job> jobList;
    //list for filling listView
    private List<MasterJob> masterJobList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        jobList = getArguments().getParcelableArrayList(OrderTabActivity.JOB_LIST_KEY);
        for (Job job : jobList){
            MasterJob masterJob = new MasterJob(job.getJobMasterId());
            int index = masterJobList.indexOf(masterJob);
            if (index > -1){
                masterJobList.get(index).numberOfOrder++;
            } else masterJobList.add(masterJob);
        }
        for(MasterJob job : masterJobList){
            Master master = RealmHelper.getMasterById(job.masterID);
            if (master != null && master.getMasterFirstName() != null){
                job.masterName = master.getMasterFirstName();
            }
        }
        Collections.sort(masterJobList, new Comparator<MasterJob>() {
            @Override
            public int compare(MasterJob masterJob, MasterJob t1) {
                return masterJob.masterName.compareTo(t1.masterName);
            }
        });
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_order_by_master, null);

        ((ListView)view.findViewById(R.id.jobListView)).setAdapter(new OrderListAdapter());
        return view;
    }

    private class OrderListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return masterJobList.size();
        }

        @Override
        public Object getItem(int i) {
            return masterJobList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_order_by_master, viewGroup, false);
                view.findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick((int)view.getTag());
                    }
                });
                view.findViewById(R.id.info_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int masterId = (int)view.getTag();
                        DialogMasterTbsInfoFragment fragment = new DialogMasterTbsInfoFragment();
                        Bundle args = new Bundle();
                        args.putInt(DialogMasterTbsInfoFragment.MASTER_ID_KEY, masterId);
                        fragment.setArguments(args);
                        fragment.show(getChildFragmentManager(), "");
                    }
                });
            }
            MasterJob job = masterJobList.get(i);
            Master master = RealmHelper.getMasterById(job.masterID);
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                    .into((ImageView) view.findViewById(R.id.master_photo));
            ((TextView) view.findViewById(R.id.master_name)).setText(job.masterName);
            String amount = getResources().getQuantityString(R.plurals.numberOfOrders, job.numberOfOrder, job.numberOfOrder);
            ((TextView) view.findViewById(R.id.amount_of_orders)).setText(amount);
            ((ImageView) view.findViewById(R.id.info_button)).setColorFilter(((BaseActivity) getActivity()).getUserTheme().getParsedMC());
            view.findViewById(R.id.info_button).setTag(job.masterID);
            //painting list items
            if (i % 2 != 0) {
                int color = ColorUtils.setAlphaComponent(((BaseActivity) getActivity()).getUserTheme().getParsedMC(), 20);
                view.findViewById(R.id.rootView).setBackgroundColor(color);
            }
            view.findViewById(R.id.rootView).setTag(i);

            return view;
        }
    }

    private void onItemClick(int position){
        int masterId = masterJobList.get(position).masterID;
        ArrayList<Job> masterJobList = new ArrayList<>();
        for (Job job : jobList){
            if (job.getJobMasterId().equals(masterId)) masterJobList.add(job);
        }
        Intent intent = new Intent(getActivity(), JobListActivity.class);
        intent.putParcelableArrayListExtra(JobListActivity.JOB_LIST_KEY, masterJobList);
        startActivity(intent);
    }

    private class MasterJob{
        public String masterName;
        public int masterID;
        public int numberOfOrder = 1;

        public MasterJob(int masterID){
            this.masterID = masterID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MasterJob masterJob = (MasterJob) o;

            return masterID == masterJob.masterID;

        }

        @Override
        public int hashCode() {
            return masterID;
        }
    }
}
