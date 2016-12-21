package com.nailonline.client.master;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nailonline.client.R;
import com.nailonline.client.entity.Master;

import java.util.List;

/**
 * Created by Roman T. on 21.12.2016.
 */

public abstract class BaseMasterListFragment extends Fragment {

    protected List<Master> masterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View fragmentView = layoutInflater.inflate(getLayoutId(), container, false);
        initRecyclerView(fragmentView);
        masterList = ((MasterTabActivity)getActivity()).getMasterList();
        return fragmentView;
    }

    protected void initRecyclerView(View view){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.master_recycler_view);
        if (recyclerView == null) return;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(getAdapter());
    }

    protected abstract int getLayoutId();
    protected abstract RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter();
}
