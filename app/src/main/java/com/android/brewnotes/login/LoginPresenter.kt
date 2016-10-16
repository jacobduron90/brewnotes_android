package com.android.brewnotes.login

import com.android.brewnotes.User
import com.android.brewnotes.service.UserManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/14/16.
 */
class LoginPresenter @Inject constructor(val userManager : UserManager){

    private var loginView : LoginView? = null;
    fun attach(loginView : LoginView){
        this.loginView = loginView;
    }

    fun detach() {
        this.loginView = null;
    }

    fun requestLogin() {
        val email = loginView?.getEmail();
        val password = loginView?.getPassword();
        userManager.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Subscriber<User>(){
                override fun onError(e: Throwable?) {
                    loginView?.loginError(e);
                }

                override fun onNext(t: User?) {
                    loginView?.loginSuccessful(t?.token);
                }

                override fun onCompleted() {

                }
            });


    }

    fun checkValidUser() {
        if(userManager.authToken != null){
            loginView?.loginSuccessful(userManager.authToken);
        }
    }



    interface LoginView {
        fun getEmail() : String;
        fun getPassword() : String;
        fun loginError(t : Throwable?);
        fun loginSuccessful(token : String?);
    }


}


