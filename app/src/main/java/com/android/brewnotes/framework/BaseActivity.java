package com.android.brewnotes.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.Injector;
import com.android.brewnotes.R;
import com.android.brewnotes.framework.BrewApplication;
import com.android.brewnotes.service.UserManager;

import javax.inject.Inject;

/**
 * Created by jacobduron on 9/5/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                ErrorHandler.bounceToLogin(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
