package com.nailonline.client.order.makenew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Roman T. on 01.02.2017.
 */

public class SelectSkillActivity extends BaseActivity {

    public List<Skill> skillList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_skill;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        int masterId = getIntent().getIntExtra(NewOrderActivity.TAG_MASTER_ID, -1);
        if (masterId < 0){
            //TODO Toast with error
            finish();
            return;
        }
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        skillList = RealmHelper.getSkillsByMaster(masterId);
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.stickyList);
        SkillListAdapter adapter = new SkillListAdapter(this, skillList);
        stickyList.setAdapter(adapter);
        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int skillId = skillList.get(i).getSkillId();
                Intent intent = new Intent();
                intent.putExtra(NewOrderActivity.TAG_SKILL_ID, skillId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
