package com.example.alexey.contacts.activities.add_contact;

import android.text.TextUtils;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.data.Contact;
import com.example.alexey.contacts.data.source.local.ContactsLocalDataSource;
import com.example.alexey.contacts.utils.schedulers.BaseSchedulerProvider;

import static com.example.alexey.contacts.activities.add_contact.CreateContactRelations.Presenter;
import static com.example.alexey.contacts.activities.add_contact.CreateContactRelations.View;
import static com.example.alexey.contacts.utils.ToastUtils.showMessage;

/**
 * Created by alexey on 02/03/17.
 */

public class CreateContactPresenter implements Presenter {

    private ContactsLocalDataSource mLocalDataSource;

    private View mView;


    CreateContactPresenter(View view, BaseSchedulerProvider schedulerProvider) {
        mView = view;

        mLocalDataSource =
                ContactsLocalDataSource.getInstance(mView.getContext(), schedulerProvider);
    }

    @Override
    public void tryToSave(String firstName, String secondName, String phone, String email) {
        if (fieldsValid(firstName, email)) {
            mLocalDataSource.saveContact(new Contact(firstName, secondName, phone, email));

            mView.onSaved();
        }
    }

    private boolean fieldsValid(String firstName, String email) {
        boolean valid = true;

        if (firstName.equals("")) {
            showMessage(R.string.message_not_valid_name, mView.getContext());
            valid = false;
        } else if (!isValidEmail(email)) {
            showMessage(R.string.message_not_valid_email, mView.getContext());
            valid = false;
        }

        return valid;

    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
