package com.example.alexey.contacts.activities.add_contact;

import android.content.Context;

/**
 * Created by alexey on 02/03/17.
 */

public interface CreateContactRelations {

    interface Presenter {

        void tryToSave(String firstName, String secondName, String phone, String email);

    }

    interface View {

        Context getContext();

        void onSaved();
    }


}
