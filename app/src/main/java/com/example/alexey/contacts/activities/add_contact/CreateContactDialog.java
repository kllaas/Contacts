package com.example.alexey.contacts.activities.add_contact;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.activities.contacts.ContactsActivity;
import com.example.alexey.contacts.activities.contacts.ContactsRelations;
import com.example.alexey.contacts.utils.schedulers.SchedulerProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexey on 02/03/17.
 */

public class CreateContactDialog extends DialogFragment implements CreateContactRelations.View {

    @BindView(R.id.et_first_name)
    EditText firstName;
    @BindView(R.id.et_second_name)
    EditText secondName;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.et_email)
    EditText email;
    private CreateContactRelations.Presenter mPresenter;
    private ContactsRelations.OnCompleteListener mListener;

    public static CreateContactDialog newInstance() {
        return new CreateContactDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setRetainInstance(true);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View parentView = inflater.inflate(R.layout.fragment_create_contact, null);

        ButterKnife.bind(this, parentView);

        mPresenter = new CreateContactPresenter(this, SchedulerProvider.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).
                setTitle(R.string.dialog_create_title)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(R.string.create, null);

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            button.setOnClickListener(view ->

                    mPresenter.tryToSave(firstName.getText().toString(),
                            secondName.getText().toString(),
                            phone.getText().toString(),
                            email.getText().toString()));
        });

        dialog.setView(parentView);
        dialog.show();

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (ContactsActivity) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException("Must implement OnCompleteListener");
        }
    }

    @Override
    public void onSaved() {
        this.dismiss();
        mListener.onContactCreated();
    }
}