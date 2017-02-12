package com.nailonline.client.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Roman T. on 24.12.2016.
 */

public class NewOrderActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String TAG_MASTER_ID = "TAG_MASTER_ID";
    public static final String TAG_SKILL_ID = "TAG_SKILL_ID";

    public static final int REQUEST_CODE_SKILL = 111;

    private static final int TIME_PICKER_INTERVAL = 5;
    private static final int DEPTH_OF_APPOINTMENT = 180; //days

    public static final int
            UNIT_PERSON = 1,
            UNIT_NAIL = 2,
            UNIT_HAND = 3;

    private Master master;
    private Skill skill;
    private int numberOfUnits;
    private int currentUnit;

    private List<Calendar> disableDays = new ArrayList<>();
    private List<DutyChart> dutyList;


    @BindView(R.id.masterName)
    protected TextView masterName;

    @BindView(R.id.masterAddress)
    protected TextView masterAddress;

    @BindView(R.id.skillLabel)
    protected TextView skillLabelText;

    @BindView(R.id.price)
    protected TextView skillPriceText;

    @BindView(R.id.dateTime)
    protected TextView dateTimeText;

    @BindView(R.id.additionalLayout)
    protected View additionalLayout;
    @BindView(R.id.unitNumberText)
    protected TextView unitNumberText;

    @BindView(R.id.minusButton)
    protected ImageView minusButton;
    @BindView(R.id.plusButton)
    protected ImageView plusButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int masterId = getIntent().getIntExtra(TAG_MASTER_ID, -1);
        if (masterId >= 0) master = RealmHelper.getMasterById(masterId);
        else {
            Toast.makeText(this, R.string.unexpected_error, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        int skillId = getIntent().getIntExtra(TAG_SKILL_ID, -1);

        if (skillId > -1) skill = RealmHelper.getSkillById(skillId);

        Picasso.with(this)
                .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                .into((ImageView) findViewById(R.id.photoImageView));
        masterName.setText(master.getMasterFirstName());
        masterAddress.setText(master.getMasterLocation().getAddress());

        dateTimeText.setTextColor(getUserTheme().getParsedAC());

        skillLabelText.setTextColor(getUserTheme().getParsedAC());
        if (skill == null) {
            skillLabelText.setText(getString(R.string.select_skill));
            skillPriceText.setVisibility(View.GONE);
        } else {
            updateSkillInfo();
        }
        fillWorkingDays();
    }

    private void updateSkillInfo() {
        skillLabelText.setText(skill.getLabel());
        skillPriceText.setVisibility(View.VISIBLE);
        switch (skill.getUnitId()) {
            case UNIT_PERSON:
                if (currentUnit == UNIT_PERSON) break;
                numberOfUnits = 1;
                additionalLayout.setVisibility(View.GONE);
                currentUnit = UNIT_PERSON;
                break;
            case UNIT_NAIL:
                initAdditionalLayout(UNIT_NAIL);
                break;
            case UNIT_HAND:
                initAdditionalLayout(UNIT_HAND);
                break;
        }
        skillPriceText.setText(getPriceText(numberOfUnits));
    }

    private void initAdditionalLayout(int unit) {
        if (currentUnit != unit) {
            numberOfUnits = unit == UNIT_HAND ? 2 : 10;
            currentUnit = unit;
        }
        additionalLayout.setVisibility(View.VISIBLE);
        String unitNumberString = "";
        switch (unit) {
            case UNIT_HAND:
                unitNumberString = getResources().getQuantityString(R.plurals.numberOfHands, numberOfUnits, numberOfUnits);
                break;
            case UNIT_NAIL:
                unitNumberString = getResources().getQuantityString(R.plurals.numberOfNail, numberOfUnits, numberOfUnits);
        }
        unitNumberText.setText(unitNumberString);
        updateButtons();
    }

    private void updateButtons() {
        int minNumber = 1;
        int maxNumber = currentUnit == UNIT_HAND ? 2 : 10;
        if (numberOfUnits == minNumber) {
            minusButton.setEnabled(false);
            plusButton.setEnabled(true);
            minusButton.setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
            plusButton.setColorFilter(getUserTheme().getParsedMC());
        } else if (numberOfUnits == maxNumber) {
            plusButton.setEnabled(false);
            minusButton.setEnabled(true);
            plusButton.setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
            minusButton.setColorFilter(getUserTheme().getParsedMC());
        } else {
            plusButton.setEnabled(true);
            minusButton.setEnabled(true);
            plusButton.setColorFilter(getUserTheme().getParsedMC());
            minusButton.setColorFilter(getUserTheme().getParsedMC());
        }
    }

    private String getPriceText(int ratio) {
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.minutes, skill.getDuration() * ratio));
        builder.append(" / ");
        builder.append(getString(R.string.roubles, skill.getPrice() * ratio));
        return builder.toString();
    }

    private void fillWorkingDays() {
        dutyList = RealmHelper.getDutyChartForMaster(master.getMasterId());
        Log.d("Debugg", dutyList.toString());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);               //because need to check today but in cycle at start adding 1 day
        c.setFirstDayOfWeek(Calendar.MONDAY);
        for (int i = 0; i <= DEPTH_OF_APPOINTMENT; i++) {
            c.add(Calendar.DATE, 1);
            if (!checkDateInDuty(c)) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.setTimeInMillis(c.getTimeInMillis());
                disableDays.add(newCalendar);
            }
        }
    }

    private boolean checkDateInDuty(Calendar date) {
        boolean result = false;
        for (DutyChart dutyChart : dutyList) {
            if (dutyChart.getIsOn() == 1) {
                switch (dutyChart.getType()) {
                    case "week":
                        int weekDay = date.get(Calendar.DAY_OF_WEEK);
                        int firstWeekDay = date.getFirstDayOfWeek() - 1;
                        int dayOfWeek = (weekDay - firstWeekDay) <= 0 ? (7 - (firstWeekDay - weekDay)) : (weekDay - firstWeekDay);

                        boolean newResult = dutyChart.getDays().charAt(dayOfWeek - 1) != '0';
                        if (newResult && !result) {
                            Log.d("Debugg", "True by " + dutyChart.toString());
                        }

                        result = result || dutyChart.getDays().charAt(dayOfWeek - 1) != '0';
                        break;
                    case "cycle": {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(dutyChart.getStartDate() * 1000);
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        long checkingDateMillis = date.getTimeInMillis();
                        long startDateMillis = c.getTimeInMillis();
                        long diff = checkingDateMillis - startDateMillis;
                        long dayCount = diff / (24 * 60 * 60 * 1000);
                        int dayForPeriod = (int) dayCount % dutyChart.getDays().length();

                        boolean newResult2 = dutyChart.getDays().charAt(dayForPeriod) != '0';

                        if (newResult2 && !result) {
                            Log.d("Debugg", "True by " + dutyChart.toString());
                        }

                        result = result || dutyChart.getDays().charAt(dayForPeriod) != '0';
                        break;
                    }
                    case "period": {
                        long dateTimestamp = Calendar.getInstance().getTimeInMillis();

                        boolean newResult3 = dateTimestamp >= dutyChart.getStartDate() && dateTimestamp <= dutyChart.getFinishDate();

                        if (newResult3 && !result) {
                            Log.d("Debugg", "True by " + dutyChart.toString());
                        }

                        result = result || dateTimestamp >= dutyChart.getStartDate() && dateTimestamp <= dutyChart.getFinishDate();
                        break;
                    }
                }
            }
        }
        return result;
    }

    private List<Timepoint> getStartEndTimepoints(Calendar date){
        Calendar startDate = null;
        Calendar endDate = null;
        for (DutyChart dutyChart : dutyList) {
            if (dutyChart.getIsOn() == 1) {
                boolean isWorkingDay = false;

                switch (dutyChart.getType()) {
                    case "week":
                        int weekDay = date.get(Calendar.DAY_OF_WEEK);
                        int firstWeekDay = date.getFirstDayOfWeek() - 1;
                        int dayOfWeek = (weekDay - firstWeekDay) <= 0 ? (7 - (firstWeekDay - weekDay)) : (weekDay - firstWeekDay);
                        isWorkingDay = dutyChart.getDays().charAt(dayOfWeek - 1) != '0';
                        break;
                    case "cycle": {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(dutyChart.getStartDate() * 1000);
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        long checkingDateMillis = date.getTimeInMillis();
                        long startDateMillis = c.getTimeInMillis();
                        long diff = checkingDateMillis - startDateMillis;
                        long dayCount = diff / (24 * 60 * 60 * 1000);
                        int dayForPeriod = (int) dayCount % dutyChart.getDays().length();
                        isWorkingDay = dutyChart.getDays().charAt(dayForPeriod) != '0';
                        break;
                    }
                    case "period": {
                        long dateTimestamp = Calendar.getInstance().getTimeInMillis();
                        isWorkingDay = dateTimestamp >= dutyChart.getStartDate() && dateTimestamp <= dutyChart.getFinishDate();
                        break;
                    }
                }
                if (isWorkingDay) {
                    Calendar currentStart = Calendar.getInstance();
                    currentStart.setTimeInMillis(dutyChart.getStartDate()*1000);
                    Calendar currentFinish = Calendar.getInstance();
                    currentFinish.setTimeInMillis(dutyChart.getFinishDate()*1000);
                    if (startDate == null || startDate.compareTo(currentStart) < 0) startDate = currentStart;
                    if (endDate == null || endDate.compareTo(currentFinish) < 0) endDate = currentFinish;
                }
            }
        }
        if (startDate == null || endDate == null )return null;
        else {
            List<Timepoint> result = new ArrayList<>();
            result.add(new Timepoint(startDate.get(Calendar.HOUR_OF_DAY), startDate.get(Calendar.MINUTE)));
            result.add(new Timepoint(endDate.get(Calendar.HOUR_OF_DAY), endDate.get(Calendar.MINUTE)));
            return result;
        }
    }


    @OnClick(R.id.minusButton)
    public void onMinusClick() {
        numberOfUnits--;
        updateSkillInfo();
    }

    @OnClick(R.id.plusButton)
    public void onPlusClick() {
        numberOfUnits++;
        updateSkillInfo();
    }

    @OnClick(R.id.skillLayout)
    public void onSelectSkillClick() {
        Intent intent = new Intent(this, SelectSkillActivity.class);
        intent.putExtra(TAG_MASTER_ID, master.getMasterId());
        startActivityForResult(intent, REQUEST_CODE_SKILL);
    }

    @OnClick(R.id.dateTime)
    public void onDateTimeClick() {
        if (skill == null) {
            Toast.makeText(this, R.string.error_select_skill, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("Debug", RealmHelper.getDutyChartForMaster(master.getMasterId()).toString());
            showDateDialog();
        }
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        showTimePickerDialog(selectedDate);
                    }

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        Calendar[] disableArray = disableDays.toArray(new Calendar[disableDays.size()]);
        pickerDialog.setDisabledDays(disableArray);
        pickerDialog.setAccentColor(getUserTheme().getParsedAC());
        pickerDialog.setMinDate(Calendar.getInstance());
        pickerDialog.show(getFragmentManager(), "Datepickerdialog");
        return;
    }

    private boolean checkRegistration() {
        if (PrefsHelper.getInstance().getUserToken().isEmpty()) {
            new RegisterDialogFragment().show(getSupportFragmentManager(), "REGISTER_DIALOG");
            return false;
        } else return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SKILL:
                    int newSkillId = data.getIntExtra(TAG_SKILL_ID, -1);
                    if (newSkillId > -1) skill = RealmHelper.getSkillById(newSkillId);
                    updateSkillInfo();
            }
        }
    }

    private void showTimePickerDialog(Calendar selectedDate) {

        List<Timepoint> startEndTimepoints = getStartEndTimepoints(selectedDate);
        Timepoint startTimepoint = startEndTimepoints.get(0);
        Timepoint endTimepoint = startEndTimepoints.get(1);

        TimePickerDialog dpd = TimePickerDialog.newInstance(
                NewOrderActivity.this,
                0,
                0,
                true);
        dpd.setTimeInterval(1, 5);
        dpd.setMinTime(startTimepoint.getHour(),startTimepoint.getMinute(),0);
        dpd.setMaxTime(endTimepoint.getHour(),endTimepoint.getMinute(),0);
        dpd.setTitle("Выберите время");
        dpd.setAccentColor(getUserTheme().getParsedAC());
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }
}
