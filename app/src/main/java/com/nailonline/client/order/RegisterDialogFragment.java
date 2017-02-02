package com.nailonline.client.order;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nailonline.client.R;

/**
 * Created by Roman T. on 03.02.2017.
 */

public class RegisterDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .customView(R.layout.dialog_register_user, false)
                .positiveText("Подтвердить")
                .negativeText("Отменить");
        return builder.build();
    }
}
