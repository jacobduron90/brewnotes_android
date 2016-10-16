package com.android.brewnotes.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.R;
import com.android.brewnotes.User;
import com.android.brewnotes.dashboard.DashboardActivity;
import com.android.brewnotes.service.UserManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements LoginPresenter.LoginView {

    private final static String TAG = "MainActivity";
    @Inject LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter.attach(this);
        loginPresenter.checkValidUser();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.detach();
        super.onDestroy();
    }

    @NotNull
    @Override
    public String getEmail() {
        return ((EditText)findViewById(R.id.email_field)).getText().toString().trim();
    }

    @NotNull
    @Override
    public String getPassword() {
        return ((EditText)findViewById(R.id.password_field)).getText().toString().trim();
    }

    @OnClick(R.id.login_button)
    public void submitClicked() {
        loginPresenter.requestLogin();
    }

    @Override
    public void loginError(@Nullable Throwable t) {
        Toast.makeText(this, "Error loggin in.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessful(@Nullable String token) {
        if(token != null){
            Intent coffebagIntent = new Intent(this, DashboardActivity.class);
            coffebagIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(coffebagIntent);
            finish();
        }
    }
}
