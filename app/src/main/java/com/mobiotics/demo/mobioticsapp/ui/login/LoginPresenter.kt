package com.mobiotics.demo.mobioticsapp.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginPresenter(private var mLoginContactView: LoginContract.View) : LoginContract.Presenter {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    //private var mLoginContactView: LoginContract.View? = null

    private lateinit var googleSignInClient: GoogleSignInClient

    fun LoginPresenter(mLoginContactView: LoginContract.View) {
        this.mLoginContactView = mLoginContactView
        //  mLoginContactView.setPresenter(this)
    }

    override fun start() {
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mLoginContactView?.getDefaultClientId())
                .requestEmail()
                .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(mLoginContactView?.getActivity()!!, gso)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    override fun login() {
        val signInIntent = googleSignInClient.signInIntent
        mLoginContactView?.signIn(signInIntent)
    }

    // [START auth_with_google]
    public fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        // Log.d(LoginActivity.TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
       // mLoginContactView?.showProgressDialogView()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)

                .addOnCompleteListener(mLoginContactView?.getActivity()!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //  Log.d(LoginActivity.TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        //updateUI(user)

                        mLoginContactView?.loginSuccess()


                    } else {
                        // If sign in fails, display a message to the user.
                        mLoginContactView?.loginFailure()
                    }

                   // mLoginContactView?.hideProgressDialogView()
                }
    }

}