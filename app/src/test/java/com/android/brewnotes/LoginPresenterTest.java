package com.android.brewnotes;

import com.android.brewnotes.login.LoginPresenter;
import com.android.brewnotes.service.BrewNotesContract;
import com.android.brewnotes.service.UserManager;
import com.android.brewnotes.servicelayer.AuthenticateRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jacobduron on 10/14/16.
 */

public class LoginPresenterTest {

    UserManager userManager;
    LoginPresenter.LoginView mockLoginView;
    Observable<User> userObservable;
    LoginPresenter presenter;

    @Before
    public void setup(){
        RxSchedulerHelper.setup();
        mockLoginView = mock(LoginPresenter.LoginView.class);
        userManager = mock(UserManager.class);

        User user = new User();
        user.token = "12l43k1j2alda&(*&Q#'";
        userObservable = Observable.just(user);

        when(userManager.login(anyString(), anyString())).thenReturn(userObservable);

        presenter = new LoginPresenter(userManager);
        presenter.attach(mockLoginView);
    }

    @After public void teardown() {
        RxSchedulerHelper.teardown();
    }

    @Test public void verifyGetEmailAndPasswordCalledOnSubmit(){
        when(mockLoginView.getEmail()).thenReturn("jacobduron@gmail.com");
        when(mockLoginView.getPassword()).thenReturn("password");
        presenter.requestLogin();
        verify(mockLoginView).getEmail();
        verify(mockLoginView).getPassword();
    }
}
