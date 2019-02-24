package com.mobiotics.demo.mobioticsapp.ui.login

import android.content.Intent
import com.mobiotics.demo.mobioticsapp.BasePresenter

interface LoginContract{
    interface View  {
        fun signIn(signInIntent: Intent)
        fun getDefaultClientId(): String
        fun getActivity(): LoginActivity
        fun  showProgressDialogView()
        fun  hideProgressDialogView()
        fun  loginSuccess()
        fun  loginFailure()
    }

    interface Presenter : BasePresenter {
        fun login()

    }
}