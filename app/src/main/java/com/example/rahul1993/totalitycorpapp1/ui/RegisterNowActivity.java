package com.example.rahul1993.totalitycorpapp1.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rahul1993.totalitycorpapp1.R;
import com.example.rahul1993.totalitycorpapp1.databinding.ActivityRegisterNowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterNowActivity extends AccountAuthenticatorActivity implements View.OnClickListener {
  private static final String TAG = RegisterNowActivity.class.getSimpleName();
  private ActivityRegisterNowBinding mActivityRegisterNowBinding;
  private FirebaseAuth mAuth;
  private String mPassword;
  private String mCheckPassword;
  private String mRetypePassword;
  private String mEmail;
  private boolean mValidEmail;
  private AccountManager mAccountManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_sign_in);
    mActivityRegisterNowBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_now);
    mAuth = FirebaseAuth.getInstance();
    mActivityRegisterNowBinding.buttonRegisterNow.setOnClickListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      openWelcomeActivity(currentUser);
    } else {
//      createUser();
    }
  }

  @Override
  public void onClick(View v) {
    mEmail = mActivityRegisterNowBinding.usernameRegisterNow.getText().toString();
    mCheckPassword  = mActivityRegisterNowBinding.passwordRegisterNow.getText().toString();
    mRetypePassword = mActivityRegisterNowBinding.retypePasswordRegisterNow.getText().toString();
    mValidEmail = Utils.checkEmailValidity(mEmail);
      if(mValidEmail && mCheckPassword.length()>=6 && mRetypePassword.length()>=6){
      if (mCheckPassword.equals(mRetypePassword)) {
        mPassword = mCheckPassword;
        final Account account = new Account(mEmail, getString(R.string.account_type));
        mAccountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
//        String authTokenType = "regular";
//        String[] requiredFeature = {};
//        Bundle bundle = new Bundle();
//        mAccountManager.addAccount(getString(R.string.account_type), authTokenType, requiredFeature,
//            bundle, this, null, null);
        mAccountManager.addAccountExplicitly(account, mPassword, null);
//        Log.d(TAG, "created " + Boolean.toString(created));
        createUser();
      } else {
        Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this, R.string.warning_registration, Toast.LENGTH_SHORT).show();
    }
  }


  private void createUser() {
    mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d(TAG, "createUserWithEmail:success");
              FirebaseUser user = mAuth.getCurrentUser();
              openWelcomeActivity(user);
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "createUserWithEmail:failure", task.getException());
              Toast.makeText(RegisterNowActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
//              updateUI(null);
            }
          }
        });
  }

  private void openWelcomeActivity(FirebaseUser user) {
    Intent intent = new Intent(this, WelcomeActivity.class);
    intent.putExtra("user", user);
    startActivity(intent);
  }

  private class TokenHandler  implements AccountManagerCallback{
    @Override
    public void run(AccountManagerFuture future) {
      Log.d(TAG, "TokenHandler registration activity");
    }
  }

  private class TaskHandler extends Handler {
    public TaskHandler(Callback callback) {
      super(callback);
      Log.d(TAG, "task Handler");
    }
  }
}
