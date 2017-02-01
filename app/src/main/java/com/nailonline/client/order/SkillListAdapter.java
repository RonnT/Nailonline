package com.nailonline.client.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Roman T. on 01.02.2017.
 */

public class SkillListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Skill> skillList;
    private Context context;
    private int themeACColor;

    public SkillListAdapter(Context context, List<Skill> skillList) {
        this.context = context;
        this.skillList = skillList;
        themeACColor = ((BaseActivity)context).getUserTheme().getParsedAC();
    }

    @Override
    public int getCount() {
        return skillList.size();
    }

    @Override
    public Object getItem(int position) {
        return skillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.view_skill_list_item, parent, false);
            holder.labelText = (TextView) convertView.findViewById(R.id.labelText);
            holder.durationText = (TextView) convertView.findViewById(R.id.durationText);
            holder.priceText = (TextView) convertView.findViewById(R.id.priceText);
            holder.unitText = (TextView) convertView.findViewById(R.id.unitText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Skill skill = skillList.get(position);

        holder.labelText.setText(skill.getLabel());
        holder.labelText.setTextColor(themeACColor);
        String durationString = context.getString(R.string.minutes, skill.getDuration());
        if (skill.getPresentId() != null && RealmHelper.getPresentById(skill.getPresentId()) != null) {
            durationString += context.getString(R.string.with_present);
        }
        holder.durationText.setText(durationString);
        holder.priceText.setText(context.getString(R.string.roubles, skill.getPrice()));
        holder.priceText.setTextColor(themeACColor);
        String unitString = "";
        switch (skill.getUnitId()){
            case NewOrderActivity.UNIT_PERSON:
                unitString = context.getString(R.string.for_person);
                break;
            case NewOrderActivity.UNIT_NAIL:
                unitString = context.getString(R.string.for_nail);
                break;
            case NewOrderActivity.UNIT_HAND:
                unitString = context.getString(R.string.for_hand);
                break;
        }
        holder.unitText.setText(unitString);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.view_skill_header_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.groupText);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String templateString = RealmHelper.getSkillsTemplateById(skillList.get(position).getTemplateId()).getLabel();
        holder.text.setText(templateString);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return skillList.get(position).getTemplateId();
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView labelText;
        TextView durationText;
        TextView priceText;
        TextView unitText;
    }

}
