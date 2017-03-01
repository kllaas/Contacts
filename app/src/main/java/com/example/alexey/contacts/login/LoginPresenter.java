package com.example.alexey.contacts.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.contacts.ContactsAcitivty;
import com.example.alexey.contacts.utils.ToastUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by alexey on 01/03/17.
 */

public class LoginPresenter implements
        GoogleApiClient.OnConnectionFailedListener, LoginRelations.Presenter {

    private LoginRelations.View mView;

    private GoogleApiClient mGoogleApiClient;

    LoginPresenter(LoginRelations.View view){
        mView = view;
    }

    @Override
    public void configure(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mView.getActivity())
                .enableAutoManage(mView.getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void logIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mView.getActivity().startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN);
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            startContactsActivity();
            return;
        }

        handleFailure();
    }

    private void handleFailure() {
        ToastUtils.showMessage(R.string.message_auth_failed, mView.getActivity());
        mView.setLoadingIndicator(false);
    }

    private void startContactsActivity() {
        Intent intent = new Intent(mView.getActivity(), ContactsAcitivty.class);
        mView.getActivity().startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        handleFailure();
    }
}
