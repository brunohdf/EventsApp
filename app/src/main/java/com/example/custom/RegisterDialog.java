package com.example.custom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventsapp.R;

public class RegisterDialog extends DialogFragment {

    private TextInputLayout mFullnameInputLayout;
    private EditText mFullname;
    private TextInputLayout mEmailInputLayout;
    private EditText mEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.register_dialog)
                .setPositiveButton("ok", null)
                .setNegativeButton("cancelar", null)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        mFullnameInputLayout = (TextInputLayout) alertDialog.findViewById(R.id.textinputlayout_fullname);
        mFullname = (EditText) alertDialog.findViewById(R.id.edittext_fullname);
        mEmailInputLayout = (TextInputLayout) alertDialog.findViewById(R.id.textinputlayout_email);
        mEmail = (EditText) alertDialog.findViewById(R.id.edittext_email);

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (send()) {
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestFocus(view.findViewById(R.id.edittext_fullname));
    }

    boolean send() {

        boolean isValid = validateFullName() && validateEmail();
        if (isValid) {
            Toast.makeText(getContext(), "Obrigado!", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateFullName() {
        if (mFullname.getText().toString().trim().isEmpty()) {
            mFullnameInputLayout.setError(getString(R.string.err_msg_fullname));
            requestFocus(mFullname);
            return false;
        } else {
            mFullnameInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = mEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            mEmailInputLayout.setError(getString(R.string.err_msg_email));
            requestFocus(mEmail);
            return false;
        } else {
            mEmailInputLayout.setErrorEnabled(false);
        }

        return true;
    }
}
