package com.example.alexey.contacts.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.alexey.contacts.R;

/**
 * Created by alexey on 01/03/17.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void setLoadingIndicator(boolean active) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }

        if (active) {
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(getString(R.string.message_loading));
            mProgressDialog.show();
        } else {
            mProgressDialog.cancel();
        }
    }

}
