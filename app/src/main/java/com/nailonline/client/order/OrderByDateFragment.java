package com.nailonline.client.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nailonline.client.R;
import com.nailonline.client.entity.Job;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class OrderByDateFragment extends Fragment {

    private ArrayList<Job> jobList;

    private TreeMap<LocalDate, ArrayList<Job>> dateJobMap = new TreeMap<>();
    private List<LocalDate> keyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        jobList = getArguments().getParcelableArrayList(OrderTabActivity.JOB_LIST_KEY);
        Collections.sort(jobList, new Comparator<Job>() {
            @Override
            public int compare(Job job, Job job2) {
                return (int) (job2.getStartDate() - job.getStartDate());
            }
        });

        for (Job job : jobList){
            LocalDate date = new LocalDate(job.getStartDate()*1000);
            if (dateJobMap.containsKey(date)){
                dateJobMap.get(date).add(job);
            } else {
                ArrayList<Job> jobList = new ArrayList<>();
                jobList.add(job);
                dateJobMap.put(date, jobList);
                keyList.add(date);
            }
        }
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_order_by_master, null);

        ((ListView)view.findViewById(R.id.jobListView)).setAdapter(new OrderByDateFragment.OrderListAdapter());
        return view;
    }

    private void onItemClick(LocalDate dateKey){
        ArrayList<Job> clickedJobList = dateJobMap.get(dateKey);
        Intent intent = new Intent(getActivity(), JobListActivity.class);
        intent.putParcelableArrayListExtra(JobListActivity.JOB_LIST_KEY, clickedJobList);
        startActivity(intent);
    }

    private class OrderListAdapter extends BaseAdapter{

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        @Override
        public int getCount() {
            return keyList.size();
        }

        @Override
        public Object getItem(int i) {
            return keyList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_order_by_date, viewGroup, false);
                view.findViewById(R.id.rootLayout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick((LocalDate)view.getTag());
                    }
                });
            }
            LocalDate date = keyList.get(i);
            List<Job> jobList = dateJobMap.get(date);
            Skill skill = null;

            String dateString = dateFormat.format(date.toDate());
            ((TextView) view.findViewById(R.id.order_date)).setText(dateString);

            if (jobList.size() == 1){
                Job job = jobList.get(0);
                skill = RealmHelper.getSkillById(job.getJobSkillId());
                StringBuilder builder = new StringBuilder();
                builder.append(timeFormat.format(job.getStartDate()*1000));
                builder.append(" - ");
                builder.append(timeFormat.format(job.getEndDate()*1000));
                builder.append(" ");
                builder.append(skill.getLabel());
                ((TextView) view.findViewById(R.id.order_info)).setText(builder.toString());
            } else {
                ((TextView)view.findViewById(R.id.order_info)).setText("");
            }
            ((TextView) view.findViewById(R.id.amount)).setText(String.valueOf(jobList.size()));
            ((TextView) view.findViewById(R.id.orderText)).setText(getResources().getQuantityString(R.plurals.numberOfOrdersWithoutDigit, jobList.size()));
            view.setTag(date);
            return view;
        }
    }
}
