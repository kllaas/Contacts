package com.example.alexey.contacts.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by alexey on 02/03/17.
 */

public interface BaseRelations {

    interface Presenter {

        void configure();

    }

    interface View {

        AppCompatActivity getActivity();

        void setLoadingIndicator(boolean b);

    }

}
