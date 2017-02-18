package com.nailonline.client.order;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.Job;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.order.makenew.NewOrderActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_HAND;
import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_NAIL;
import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_PERSON;

/**
 * Created by Roman T. on 17.02.2017.
 */

public class JobListActivity extends BaseActivity {

    private static final SparseArray<String> statusArray;

    static {
        statusArray = new SparseArray<>(9);
        statusArray.put(1, "Забронирована");
        statusArray.put(2, "Подтверждена");
        statusArray.put(3, "Перенесена");
        statusArray.put(4, "Отклонена");
        statusArray.put(5, "Выполнена");
        statusArray.put(6, "Отменена");
        statusArray.put(7, "Оплачена");
        statusArray.put(8, "Не оплачена");
        statusArray.put(9, "Просрочена");
    }

    public static final String JOB_LIST_KEY = "JOB_LIST_KEY";

    List<Job> itemList;

    private RecyclerView.Adapter<JobViewHolder> adapter;

    @BindView(R.id.orderRecyclerView)
    RecyclerView recyclerView;

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int position = (int) view.getTag();
            final Job job = itemList.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(JobListActivity.this);
            builder.setMessage(R.string.cancel_order);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ApiVolley.getInstance().setJobState(job.getJobId(), 6, job.getJobComments(), job.getStartDate(), job.getEndDate(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(JobListActivity.this,"Запись успешно отменена", Toast.LENGTH_LONG).show();
                                    itemList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(JobListActivity.this,"При отмене записи произошла ошибка", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(JobListActivity.this,"При отмене записи произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getUserTheme().getParsedAC());
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getUserTheme().getParsedAC());
                }
            });
            dialog.show();

        }
    };

    private View.OnClickListener onRepeatClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            Job job = itemList.get(position);
            Intent intent = new Intent(JobListActivity.this, NewOrderActivity.class);
            intent.putExtra(NewOrderActivity.TAG_MASTER_ID, job.getJobMasterId());
            intent.putExtra(NewOrderActivity.TAG_SKILL_ID, job.getJobSkillId());
            startActivity(intent);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        itemList = getIntent().getParcelableArrayListExtra(JOB_LIST_KEY);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getAdapter());
    }

    private RecyclerView.Adapter<JobViewHolder> getAdapter() {
        if (adapter == null) adapter = new JobListAdapter();
        return adapter;
    }

    private class JobListAdapter extends RecyclerView.Adapter<JobViewHolder> {

        @Override
        public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_card_item, parent, false);
            return new JobViewHolder(view);
        }

        @Override
        public void onBindViewHolder(JobViewHolder holder, int position) {
            Job job = itemList.get(position);
            Master master = RealmHelper.getMasterById(job.getJobMasterId());
            Skill skill = RealmHelper.getSkillById(job.getJobSkillId());
            Picasso.with(JobListActivity.this)
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                    .into(holder.photoView);
            holder.masterName.setText(master.getMasterFirstName());
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String date = format.format(job.getStartDate()*1000);
            holder.jobDate.setText(date);
            holder.jobStatus.setText(statusArray.get(job.getJobStateId()));
            holder.jobLabel.setText(skill.getLabel());
            String description = (job.getEndDate() - job.getStartDate()) / 60 + " мин, " + job.getPrice() + " руб. ";
            switch (skill.getUnitId()) {
                case UNIT_PERSON:
                    description += "за услугу";
                    break;
                case UNIT_NAIL:
                    description += getResources().getQuantityString(R.plurals.forNumberOfNail, job.getJobAmount(), job.getJobAmount());
                    break;
                case UNIT_HAND:
                    description += getResources().getQuantityString(R.plurals.forNumberOfHands, job.getJobAmount(), job.getJobAmount());
                    break;
            }
            holder.jobDescription.setText(description);
            holder.filledButton.setText(getFilledButtonText(job.getJobStateId()));
            holder.filledButton.getBackground().setColorFilter(getUserTheme().getParsedMC(), PorterDuff.Mode.MULTIPLY);
            holder.filledButton.setTextColor(getUserTheme().getParsedWC());
            holder.filledButton.setTag(position);
            holder.filledButton.setOnClickListener(getFilledButtonClick(job.getJobStateId()));

            if (getBlankButtonText(job.getJobStateId()) != null) {
                holder.blankButtonLayout.setVisibility(View.VISIBLE);
                holder.blankButton.setText(getBlankButtonText(job.getJobStateId()));
                holder.blankButton.getBackground().setColorFilter(getUserTheme().getParsedWC(), PorterDuff.Mode.MULTIPLY);
                holder.blankButton.setTextColor(getUserTheme().getParsedMC());
                holder.blankButton.setText(getBlankButtonText(job.getJobStateId()));
                holder.blankButton.setTag(position);
                holder.blankButton.setOnClickListener(getBlankButtonClick(job.getJobStateId()));
            } else holder.blankButtonLayout.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    private String getFilledButtonText(int statusId) {
        switch (statusId) {
            case 5:
                return "Оплатить";
            default:
                return "Повторить";
        }
    }

    private String getBlankButtonText(int statusId) {
        switch (statusId) {
            case 1:
                return "Отменить";
            case 4:
                return null;
            case 5:
                return "Повторить";
            case 6:
                return null;
            default:
                return null;
        }
    }

    private View.OnClickListener getBlankButtonClick(int statusId) {
        if (statusId == 1) return onCancelClickListener;
        else return null;
    }

    private View.OnClickListener getFilledButtonClick(int statusId) {
        if (statusId == 5) return null;
        else return onRepeatClickListener;
    }

    private class JobViewHolder extends RecyclerView.ViewHolder {

        ImageView photoView;
        TextView masterName;
        TextView jobDate;
        TextView jobStatus;
        TextView jobLabel;
        TextView jobDescription;
        Button filledButton;
        Button blankButton;
        View blankButtonLayout;

        public JobViewHolder(View itemView) {
            super(itemView);
            photoView = (ImageView) itemView.findViewById(R.id.master_photo);
            masterName = (TextView) itemView.findViewById(R.id.master_name);
            jobDate = (TextView) itemView.findViewById(R.id.job_date);
            jobStatus = (TextView) itemView.findViewById(R.id.job_status);
            jobLabel = (TextView) itemView.findViewById(R.id.job_label);
            jobDescription = (TextView) itemView.findViewById(R.id.job_description);
            filledButton = (Button) itemView.findViewById(R.id.filled_button);
            blankButton = (Button) itemView.findViewById(R.id.blank_button);
            blankButtonLayout = itemView.findViewById(R.id.blank_button_layout);
        }
    }
}
