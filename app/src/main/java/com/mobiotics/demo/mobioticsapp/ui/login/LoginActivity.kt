package com.mobiotics.demo.mobioticsapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.mobiotics.demo.mobioticsapp.BaseActivity
import com.mobiotics.demo.mobioticsapp.R
import com.mobiotics.demo.mobioticsapp.ui.videolist.VideoListActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class LoginActivity : BaseActivity(), View.OnClickListener, LoginContract.View {


    // private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Button listeners
        signInButtonLogin.setOnClickListener(this)
        loginPresenter = LoginPresenter(this)
        loginPresenter.start()
        loginPresenter

    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                loginPresenter.firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun signIn(signInIntent: Intent) {

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.signInButtonLogin ->
                loginPresenter.login()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun getDefaultClientId(): String {
        return getString(R.string.default_web_client_id)
    }

    override fun getActivity(): LoginActivity {
        return this
    }

    override fun showProgressDialogView() {
        showProgressDialogView()
    }

    override fun hideProgressDialogView() {
        hideProgressDialogView()
    }

    override fun loginSuccess() {
        loginSucess()
        val intent = Intent(this, VideoListActivity::class.java)

        intent.putExtra("keyIdentifier", "test")
        startActivity(intent)
        finish()
    }

    override fun loginFailure() {
        Snackbar.make(main_layoutlogin, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
     }

    fun loginSucess() {
        Snackbar.make(main_layoutlogin, "Authentication Success.", Snackbar.LENGTH_SHORT).show()
    }
}
