package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nailonline.client.entity.SkillsTemplate;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.master.MasterTabActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman T. on 20.01.2017.
 */

public class ServiceListActivity extends BaseActivity {

    private List<SkillsTemplate> itemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_list;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemList.clear();
        itemList.addAll(RealmHelper.getAllSkillsTemplates());
        initViews();
    }

    private void initViews(){
        ListView listView = (ListView) findViewById(R.id.serviceList);
        ServiceListAdapter adapter = new ServiceListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMastersWithTemplate(itemList.get(i));
            }
        });
    }

    private void showMastersWithTemplate(SkillsTemplate template){
        Intent intent = new Intent(this, MasterTabActivity.class);
        intent.putExtra(MasterTabActivity.TEMPLATE_ID_KEY, template.getTemplateId());
        startActivity(intent);
    }

    private class ServiceListAdapter extends BaseAdapter{

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
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.view_service_item, viewGroup, false);
            }
            SkillsTemplate skillsTemplate = (SkillsTemplate) getItem(i);
            TextView textItem = (TextView)view.findViewById(R.id.itemText);
            textItem.setText(skillsTemplate.getLabel());
            textItem.setTextColor(getUserTheme().getParsedAC());
            return view;
        }
    }
}
