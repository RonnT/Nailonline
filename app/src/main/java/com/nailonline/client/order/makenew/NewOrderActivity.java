package com.nailonline.client.order.makenew;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Present;
import com.nailonline.client.entity.ShortJob;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.extension.CloseKeyboardEditText;
import com.nailonline.client.extension.CustomTimePickerDialog;
import com.nailonline.client.helper.ParserHelper;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.server.ResponseHttp;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Roman T. on 24.12.2016.
 */

public class NewOrderActivity extends BaseActivity implements TextView.OnEditorActionListener {

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

    protected View mFocusLayout;

    private List<Calendar> disableDays = new ArrayList<>();
    private List<DutyChart> dutyList;

    private Calendar selectedDate;
    private List<ShortJob> jobList;

    private String userPhone;

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

    @BindView(R.id.commentEditText)
    protected EditText commentEditText;

    @BindView(R.id.presentLayout)
    protected ViewGroup presentLayout;
    @BindView(R.id.presentImage)
    protected ImageView presentImage;
    @BindView(R.id.presentText)
    protected TextView presentText;
    @BindView(R.id.submitButton)
    protected Button submitButton;

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
        updateSkillInfo();
        setCursorColor(commentEditText, getUserTheme().getParsedAC());
        setFocusLayoutOnEdits((ViewGroup) findViewById(R.id.activityContent));
        submitButton.getBackground().setColorFilter(getUserTheme().getParsedMC(), PorterDuff.Mode.MULTIPLY);
        submitButton.getBackground().setAlpha(100);
        submitButton.setTextColor(getUserTheme().getParseWC());
        fillWorkingDays();
    }

    // next three methods using for disable focus on edittext after finish editing
    protected void setFocusLayoutOnEdits(ViewGroup root) {
        if (mFocusLayout == null) mFocusLayout = findViewById(R.id.activityContent);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);

            if (v instanceof ViewGroup && ((ViewGroup) v).getChildCount() != 0) {
                setFocusLayoutOnEdits((ViewGroup) v);
            } else if (v instanceof CloseKeyboardEditText) {
                ((CloseKeyboardEditText) v).setFocusLayout(mFocusLayout);
                ((CloseKeyboardEditText) v).setOnEditorActionListener(this);
            }
        }
    }

    protected void removeFocus() {
        try {
            View textView = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            if (mFocusLayout != null) mFocusLayout.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            removeFocus();
            return true;
        }
        return false;
    }

    private void updateSkillInfo() {
        if (skill == null) {
            skillLabelText.setText(getString(R.string.select_skill));
            skillPriceText.setVisibility(View.GONE);
            additionalLayout.setVisibility(View.GONE);
        } else {
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
        checkSubmitButton();
        checkAndFillPresent();
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

    private void checkAndFillPresent() {
        if (skill == null ||
                skill.getPresentId() == null ||
                RealmHelper.getPresentById(skill.getPresentId()) == null) {
            presentLayout.setVisibility(View.GONE);
            return;
        }
        presentLayout.setVisibility(View.VISIBLE);
        Present present = RealmHelper.getPresentById(skill.getPresentId());
        presentText.setText(present.getLabel());
        presentText.setTextColor(getUserTheme().getParsedAC());
        Picasso.with(this)
                .load(BuildConfig.SERVER_IMAGE_PRESENTS + present.getPhotoName())
                .into(presentImage);
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

    // returns list of two Timepoints (start and end working day of master) for selected date
    private List<Timepoint> getStartEndTimepoints(Calendar date) {
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
                    currentStart.setTimeInMillis(dutyChart.getStartDate() * 1000);
                    Calendar currentFinish = Calendar.getInstance();
                    currentFinish.setTimeInMillis(dutyChart.getFinishDate() * 1000);
                    if (startDate == null || startDate.compareTo(currentStart) < 0)
                        startDate = currentStart;
                    if (endDate == null || endDate.compareTo(currentFinish) < 0)
                        endDate = currentFinish;
                }
            }
        }
        if (startDate == null || endDate == null) return null;
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
        clearDateTime();
    }

    @OnClick(R.id.plusButton)
    public void onPlusClick() {
        numberOfUnits++;
        updateSkillInfo();
        clearDateTime();
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
        Calendar entryDate;
        if (selectedDate == null) {
            entryDate = Calendar.getInstance();
        } else entryDate = selectedDate;
        DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar date = Calendar.getInstance();
                        date.set(year, monthOfYear, dayOfMonth);
                        selectedDate = date;
                        getShortJobs();
                    }

                },
                entryDate.get(Calendar.YEAR),
                entryDate.get(Calendar.MONTH),
                entryDate.get(Calendar.DAY_OF_MONTH)
        );
        pickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                selectedDate = null;
            }
        });
        Calendar[] disableArray = disableDays.toArray(new Calendar[disableDays.size()]);
        pickerDialog.setDisabledDays(disableArray);
        pickerDialog.setAccentColor(getUserTheme().getParsedAC());
        pickerDialog.setMinDate(Calendar.getInstance());
        pickerDialog.show(getFragmentManager(), "Datepickerdialog");
        return;
    }

    private void getShortJobs() {
        Calendar startDay = Calendar.getInstance();
        startDay.setTimeInMillis(selectedDate.getTimeInMillis());
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        Calendar endDay = Calendar.getInstance();
        endDay.setTimeInMillis(selectedDate.getTimeInMillis());
        endDay.set(Calendar.HOUR_OF_DAY, 23);
        endDay.set(Calendar.MINUTE, 59);
        endDay.set(Calendar.SECOND, 59);
        ApiVolley.getInstance().getMasterShortJobs(
                master.getMasterId(),
                startDay.getTimeInMillis() / 1000,
                endDay.getTimeInMillis() / 1000,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ResponseHttp responseHttp = new ResponseHttp(response);
                        if (responseHttp != null) {
                            if (responseHttp.getError() != null) return;
                            try {
                                jobList = ParserHelper.parseShortJobs(response);
                                Log.d("Debugg", jobList.toString());
                                showTimePickerDialog();
                            } catch (JSONException e) {
                                //TODO make error
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO make error
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SKILL:
                    int newSkillId = data.getIntExtra(TAG_SKILL_ID, -1);
                    if (newSkillId > -1) skill = RealmHelper.getSkillById(newSkillId);
                    updateSkillInfo();
                    clearDateTime();
            }
        }
    }

    private void showTimePickerDialog() {
        List<Timepoint> startEndTimepoints = getStartEndTimepoints(selectedDate);
        Timepoint startTimepoint = startEndTimepoints.get(0);
        Timepoint endTimepoint = startEndTimepoints.get(1);

        CustomTimePickerDialog dpd = CustomTimePickerDialog.newInstance(
                new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(CustomTimePickerDialog view, int hourOfDay, int minute, int second) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(selectedDate.getTimeInMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, second);
                        if (isTimeAvailable(calendar)) {
                            selectedDate.setTimeInMillis(calendar.getTimeInMillis());
                            fillDateTime();
                            checkSubmitButton();
                            view.dismiss();
                        } else
                            Toast.makeText(NewOrderActivity.this, "Время занято", Toast.LENGTH_SHORT).show();
                    }
                },
                0,
                0,
                true);
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                showDateDialog();
            }
        });
        dpd.setAutoDismissOnOk(false);
        dpd.setCancelText("Назад");
        dpd.setTimeInterval(1, 5);
        dpd.setMinTime(startTimepoint.getHour(), startTimepoint.getMinute(), 0);
        dpd.setMaxTime(endTimepoint.getHour(), endTimepoint.getMinute(), 0);
        dpd.setTitle("Выберите время");
        dpd.setAccentColor(getUserTheme().getParsedAC());
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void fillDateTime() {
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(selectedDate.getTimeInMillis());
        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(startTime.getTimeInMillis());
        endTime.add(Calendar.MINUTE, skill.getDuration() * numberOfUnits);
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String string = format.format(selectedDate.getTime()) + " c " + formatTime.format(startTime.getTime())
                + " до " + formatTime.format(endTime.getTime());
        dateTimeText.setText(string);
    }

    private void checkSubmitButton() {
        if (skill != null && selectedDate != null) {
            submitButton.setEnabled(true);
            submitButton.getBackground().setAlpha(255);
        } else {
            submitButton.setEnabled(false);
            submitButton.getBackground().setAlpha(100);
        }
    }

    private void clearDateTime() {
        dateTimeText.setText(R.string.select_date_time);
        selectedDate = null;
        checkSubmitButton();
    }

    private boolean isTimeAvailable(Calendar calendar) {
        boolean timeBusy = false;
        long timestamp = calendar.getTimeInMillis() / 1000;
        for (ShortJob shortJob : jobList) {
            if (shortJob.getStartDate().compareTo(timestamp) <= 0 && shortJob.getEndDate().compareTo(timestamp) > 0) {
                timeBusy = true;
            }
        }
        return !timeBusy;
    }

    public static void setCursorColor(EditText view, @ColorInt int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }

    @OnClick(R.id.submitButton)
    public void onSubmitClick() {
        if (PrefsHelper.getInstance().getUserToken().isEmpty()) {
            new RegisterDialogFragment().show(getSupportFragmentManager(), "REGISTER_DIALOG");
        } else {
            ApiVolley.getInstance().addJob(
                    master.getMasterId(),
                    skill.getSkillId(),
                    numberOfUnits,
                    master.getMasterMainLocationId(),
                    selectedDate.getTimeInMillis() / 1000,
                    commentEditText.getText().toString(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(NewOrderActivity.this, R.string.order_created_successfully, Toast.LENGTH_LONG).show();
                                    clearAllFields();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TODO error processing
                        }
                    }
            );
            //TODO send new order
        }
    }

    private void clearAllFields() {
        skill = null;
        selectedDate = null;
        currentUnit = 0;
        updateSkillInfo();
        clearDateTime();
        commentEditText.setText("");
    }

    public void sendPhone(String phone) {
        phone = phone.substring(1, phone.length());
        userPhone = phone;
        ApiVolley.getInstance().getUserCode(phone, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        showRegisterCodeDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    userPhone = null;
                    //TODO error processing
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO error processing
                userPhone = null;
            }
        });
    }

    private void showRegisterCodeDialog() {
        new CodeDialogFragment().show(getSupportFragmentManager(), "REGISTER_CODE_DIALOG");
    }

    public void sendCode(String code) {
        ApiVolley.getInstance().getUserToken(userPhone, code, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success") && !TextUtils.isEmpty(response.getString("token"))) {
                        PrefsHelper.getInstance().setUserToken(response.getString("token"));
                    }
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
}
