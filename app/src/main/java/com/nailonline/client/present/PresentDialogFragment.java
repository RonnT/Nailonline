package com.nailonline.client.present;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nailonline.client.R;
import com.nailonline.client.entity.Present;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.order.NewOrderActivity;

import java.util.List;

/**
 * Created by Roman T. on 29.01.2017.
 */

public class PresentDialogFragment extends DialogFragment {

    public List<Present> presentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        presentList = RealmHelper.getAllPresents();

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_presents, null);
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e){
            e.printStackTrace();
        }
        initGridView(view);
        return view;
    }

    private void initGridView(View view){
        GridView gridView = (GridView) view.findViewById(R.id.gridview_presents);
        PresentGridViewAdapter adapter = new PresentGridViewAdapter(getActivity(), presentList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                makeNewOrder(presentList.get(i).getMasterId());
            }
        });
    }

    private void makeNewOrder(int masterId){
        Intent intent = new Intent(getActivity(), NewOrderActivity.class);
        intent.putExtra(NewOrderActivity.TAG_MASTER_ID, masterId);
        startActivity(intent);
    }
}
