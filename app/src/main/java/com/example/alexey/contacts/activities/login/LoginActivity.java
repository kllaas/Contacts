package com.example.alexey.contacts.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.activities.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginRelations.View {

    static final int RC_SIGN_IN = 9001;

    LoginRelations.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);
        mPresenter.configure();
    }

    @OnClick(R.id.sign_in_button)
    public void onClick() {
        mPresenter.logIn();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.handleSignInResult(result);
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }
}
