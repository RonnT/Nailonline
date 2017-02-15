package com.nailonline.client.order.makenew;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class CodeDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_code_register_user, null);
        final EditText codeEditText = ((EditText)view.findViewById(R.id.codeEditText));
        int colorAC = ((BaseActivity)getActivity()).getUserTheme().getParsedAC();
        NewOrderActivity.setCursorColor(codeEditText, colorAC);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .customView(view, false)
                .positiveText("Подтвердить")
                .positiveColor(colorAC)
                .negativeText("Отменить")
                .negativeColor(colorAC)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO add check code
                        if (codeEditText.getText().length() != 5){
                            Toast.makeText(getContext(), "Введите код из 5 цифр", Toast.LENGTH_SHORT).show();
                        } else {
                            ((NewOrderActivity)getActivity()).sendCode(codeEditText.getText().toString());
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
}
