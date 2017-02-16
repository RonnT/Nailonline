package com.nailonline.client.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.nailonline.client.entity.Job;

import java.util.ArrayList;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class OrderByMasterFragment extends Fragment {

    private ArrayList<Job> jobList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobList = getArguments().getParcelableArrayList(OrderTabActivity.JOB_LIST_KEY);
        
    }
}
