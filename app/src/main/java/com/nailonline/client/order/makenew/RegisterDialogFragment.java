package com.nailonline.client.order.makenew;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;

/**
 * Created by Roman T. on 03.02.2017.
 */

public class RegisterDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_register_user, null);
        final EditText nameEditText = ((EditText)view.findViewById(R.id.nameEditText));
        int colorAC = ((BaseActivity)getActivity()).getUserTheme().getParsedAC();
        final EditText phoneEditText = ((EditText)view.findViewById(R.id.phoneEditText));
        NewOrderActivity.setCursorColor(nameEditText, colorAC);
        NewOrderActivity.setCursorColor(phoneEditText, colorAC);
        ((EditText)view.findViewById(R.id.phoneEditText)).addTextChangedListener(new PhoneWatcher((EditText)view.findViewById(R.id.phoneEditText)));
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .customView(view, false)
                .positiveText("Получить код")
                .positiveColor(colorAC)
                .negativeText("Отменить")
                .negativeColor(colorAC)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO add check phone

                        if (TextUtils.isEmpty(nameEditText.getText()) || TextUtils.isEmpty(phoneEditText.getText()) ){
                            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                        } else {
                            ((NewOrderActivity)getActivity()).sendPhone(phoneEditText.getText().toString());
                            dialog.dismiss();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        return builder.build();
    }

    public class PhoneWatcher implements TextWatcher {

        private EditText mEtPhone;

        public PhoneWatcher(EditText pEtPhone){
            mEtPhone = pEtPhone;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();

            if (text.length() < 2) {
                mEtPhone.setText("+7");
                mEtPhone.setSelection(2);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}
