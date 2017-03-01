package com.example.alexey.contacts.activities.login;

import com.example.alexey.contacts.activities.BaseRelations;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by alexey on 01/03/17.
 */

public interface LoginRelations {

    interface Presenter extends BaseRelations.Presenter {

        void logIn();

        void handleSignInResult(GoogleSignInResult result);
    }

    interface View extends BaseRelations.View {

    }

}
