package com.nailonline.client.order;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.Job;
import com.nailonline.client.entity.JobState;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.ParserHelper;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.order.makenew.NewOrderActivity;
import com.nailonline.client.api.ErrorHttp;
import com.nailonline.client.api.ResponseHttp;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nailonline.client.order.OrderTabActivity.parseJobs;
import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_HAND;
import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_NAIL;
import static com.nailonline.client.order.makenew.NewOrderActivity.UNIT_PERSON;

/**
 * Created by Roman T. on 17.02.2017.
 */

public class JobListActivity extends BaseActivity {

    public static final String JOB_LIST_KEY = "JOB_LIST_KEY";
    public static final String JOB_KEY = "JOB_KEY";

    List<Job> itemList;

    private RecyclerView.Adapter<JobViewHolder> adapter;

    @BindView(R.id.orderRecyclerView)
    RecyclerView recyclerView;

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int position = (int) view.getTag();
            final Job job = itemList.get(position);
            showCancelDialog(job, position);
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

    private View.OnClickListener onPayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int position = (int) view.getTag();
            final Job job = itemList.get(position);
            doPayment(job);
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
        //it using from push
        final int jobId = getIntent().getIntExtra(JOB_KEY, -1);

        if (jobId > 0 && (itemList == null || itemList.isEmpty())) {
            ApiVolley.getInstance().getUserJobs(0, 0, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        List<Job> jobList = parseJobs(response);
                        itemList = new ArrayList<>();
                        for (Job job : jobList) {
                            if (job.getJobId() == jobId) {
                                itemList.add(job);
                                return;
                            }
                        }
                        fillBonusPayForJobs();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        fillBonusPayForJobs();
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

    private void fillBonusPayForJobs() {
        for (Job job : itemList) {
            int bonusPay = RealmHelper.getSkillById(job.getJobSkillId()).getBonusPay();
            job.setBonusPay(bonusPay == 1);
        }
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
            String date = format.format(job.getStartDate() * 1000);
            holder.jobDate.setText(date);
            JobState state = JobState.getById(job.getJobStateId());
            if (state != null) {
                holder.jobStatus.setVisibility(View.VISIBLE);
                holder.jobStatus.setText(state.getText());
                holder.jobStatus.setTextColor(state.getColor());
            } else holder.jobStatus.setVisibility(View.GONE);
            holder.jobLabel.setText(skill.getLabel());
            String description = (job.getEndDate() - job.getStartDate()) / 60 + " мин, " + job.getPrice() + " руб. ";
            switch (skill.getUnitId()) {
                case UNIT_PERSON:
                    description += getString(R.string.for_person);
                    break;
                case UNIT_NAIL:
                    description += getResources().getQuantityString(R.plurals.forNumberOfNail, job.getJobAmount(), job.getJobAmount());
                    break;
                case UNIT_HAND:
                    description += getResources().getQuantityString(R.plurals.forNumberOfHands, job.getJobAmount(), job.getJobAmount());
                    break;
            }
            holder.jobDescription.setText(description);
            String filledButtonText = getFilledButtonText(job.getJobStateId(), job.isBonusPay());
            if (filledButtonText != null) {
                holder.filledButton.setText(filledButtonText);
                holder.filledButton.getBackground().setColorFilter(getUserTheme().getParsedMC(), PorterDuff.Mode.MULTIPLY);
                holder.filledButton.setTextColor(getUserTheme().getParsedWC());
                holder.filledButton.setTag(position);
                holder.filledButton.setOnClickListener(getFilledButtonClick(job.getJobStateId(), job.isBonusPay()));
            } else holder.filledButtonLayout.setVisibility(View.GONE);

            String blankButtonText = getBlankButtonText(job.getJobStateId(), job.isBonusPay());
            if (blankButtonText != null) {
                holder.blankButtonLayout.setVisibility(View.VISIBLE);
                holder.blankButton.getBackground().setColorFilter(getUserTheme().getParsedWC(), PorterDuff.Mode.MULTIPLY);
                holder.blankButton.setTextColor(getUserTheme().getParsedMC());
                holder.blankButton.setText(blankButtonText);
                holder.blankButton.setTag(position);
                holder.blankButton.setOnClickListener(getBlankButtonClick(job.getJobStateId()));
            } else holder.blankButtonLayout.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    private String getFilledButtonText(int statusId, boolean isBonusPay) {
        JobState state = JobState.getById(statusId);
        if (state == null) return null;
        switch (state) {
            case COMPLETE:
                if (isBonusPay) return getString(R.string.pay);
                else return getString(R.string.repeat);
            default:
                return getString(R.string.repeat);
        }
    }

    private String getBlankButtonText(int statusId, boolean isBonusPay) {
        JobState state = JobState.getById(statusId);
        if (state == null) return null;
        switch (state) {
            case REQUESTED:
                return getString(android.R.string.cancel);
            case ACCEPTED:
                return getString(android.R.string.cancel);
            case POSTPONED:
                return getString(android.R.string.cancel);
            case COMPLETE:
                if (isBonusPay) return getString(R.string.repeat);
                else return null;
            default:
                return null;
        }
    }

    private View.OnClickListener getBlankButtonClick(int statusId) {
        JobState state = JobState.getById(statusId);
        switch (state) {
            case REQUESTED:
            case ACCEPTED:
            case POSTPONED:
                return onCancelClickListener;
            case COMPLETE:
                return onRepeatClickListener;
            default:
                return null;
        }
    }

    private View.OnClickListener getFilledButtonClick(int statusId, boolean isBonusPay) {
        if (statusId == JobState.COMPLETE.getId()) {
            if (isBonusPay) return onPayClickListener;
            else return onRepeatClickListener;
        }
        //in other cases this button is 'Повторить'
        else return onRepeatClickListener;
    }

    private void showCancelDialog(final Job job, final int position) {
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
                                Toast.makeText(JobListActivity.this, R.string.cancel_successed, Toast.LENGTH_LONG).show();
                                itemList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(JobListActivity.this, R.string.cancel_error, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JobListActivity.this, R.string.cancel_error, Toast.LENGTH_LONG).show();
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

    private void doPayment(final Job job) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.customView(R.layout.dialog_pay_job, false)
                .positiveText(R.string.pay)
                .negativeText(android.R.string.cancel)
                .positiveColor(getUserTheme().getParsedAC())
                .negativeColor(getUserTheme().getParsedAC())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        sendPayRequest(job.getJobId());
                    }
                })
                .show();
    }

    private void sendPayRequest(int jobId) {
        ApiVolley.getInstance().payJob(jobId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        showAlertDialog(-1, R.string.payment_success);
                    } else showAlertDialog(R.string.error, R.string.error_unable_to_pay);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlertDialog(R.string.error, R.string.error_unable_to_pay);
            }
        });
    }

    private void refreshData() {
        //TODO progress in dialog

        ApiVolley.getInstance().getAllSkills(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(Skill.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSaveSkills(response);
                        refreshItemList();
                    } catch (JSONException e) {
                        //TODO make error
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private void refreshItemList() {
        final Set<Integer> jobIdSet = new HashSet<>();
        for (Job job : itemList) {
            jobIdSet.add(job.getJobId());
        }
        ApiVolley.getInstance().getUserJobs(0, 0, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Job> jobList = parseJobs(response);
                    itemList.clear();
                    for (Job job : jobList) {
                        if (jobIdSet.contains(job.getJobId())) {
                            itemList.add(job);
                        }
                    }
                    fillBonusPayForJobs();
                    Collections.sort(itemList, new Comparator<Job>() {
                        @Override
                        public int compare(Job job, Job job2) {
                            return (int) (job2.getStartDate() - job.getStartDate());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.job_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            refreshData();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        View filledButtonLayout;

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
            filledButtonLayout = itemView.findViewById(R.id.filled_button_layout);
        }
    }
}
