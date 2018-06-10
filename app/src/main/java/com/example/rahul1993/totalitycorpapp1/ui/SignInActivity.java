package com.example.rahul1993.totalitycorpapp1.ui;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.rahul1993.totalitycorpapp1.R;
import com.example.rahul1993.totalitycorpapp1.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by rahul1993 on 6/8/2018.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String TAG = SignInActivity.class.getSimpleName();
 private ActivitySignInBinding mActivitySignInBinding;
 private String mEmail;
 private String mPassword;
 private FirebaseAuth mAuth;
  private AccountManager mAccountManager;
  private Account[] mAccount;
  List<String> permissionNeeded;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    mAuth = FirebaseAuth.getInstance();
    int permissionCheck = ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.READ_CONTACTS);
    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      requestPermission();
    } else {
      showUI();
    }
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(SignInActivity.this,
            new String[]{Manifest.permission.READ_CONTACTS}, 151);
    }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      //Note:- Don't use 1 as it already used for something else by AppIntro in parents method
      case 151:
        if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                  showUI();
        } else {
//          handle permission denial
          showUI();
          Toast.makeText(this, "Permission required for app to function properly", Toast.LENGTH_SHORT).show();
        }
    }
  }

  private void showUI() {
    mActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    mActivitySignInBinding.buttonLogin.setOnClickListener(this);
    mActivitySignInBinding.textViewRegisterNow.setOnClickListener(this);

    mAccountManager = AccountManager.get(this);
    mAccount = mAccountManager.getAccountsByType(getString(R.string.account_type));
    if (mAccount.length >0 && mAccount[0] != null) {
      mActivitySignInBinding.accountSignInButton.setVisibility(View.VISIBLE);
      mActivitySignInBinding.textViewRegisterNow.setVisibility(View.GONE);
      mActivitySignInBinding.accountSignInButton.setOnClickListener(this);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    if(mAuth != null) {
      FirebaseUser currentUser = mAuth.getCurrentUser();
      if (currentUser != null) {
        openWelcomeActivity(currentUser);
      }
    }
  }

  private void openWelcomeActivity(FirebaseUser user) {
    Intent welcomeActivityIntent = new Intent(this, WelcomeActivity.class);
    welcomeActivityIntent.putExtra("user", user);
    startActivity(welcomeActivityIntent);
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.button_login:
        mEmail = mActivitySignInBinding.usernameLogin.getText().toString();
        mPassword = mActivitySignInBinding.passwordLogin.getText().toString();
        if(!mEmail.isEmpty() && !mPassword.isEmpty()) {
          loginUser();
        } else {
          Toast.makeText(this, R.string.warning_login_field_empty, Toast.LENGTH_SHORT).show();
        }
        break;
      case R.id.textView_register_now:
        Intent registerNowIntent = new Intent(this, RegisterNowActivity.class);
        startActivity(registerNowIntent);
        break;

      case R.id.account_sign_in_button:
        Bundle option = new Bundle();
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        Log.i(TAG, "before service call");
        AccountManagerFuture accountManagerFuture = mAccountManager.getAuthToken(mAccount[0], "",
            null, this, new OnTokenAcquired(), new Handler(new OnError()));
        Log.i(TAG, "After service call");
        Log.i(TAG, "After service call");
        Log.i(TAG, "After service call");
        Log.i(TAG, "After service call");
        Log.i(TAG, "After service call");
        Log.i(TAG, "After service call");

        break;
    }
  }

  private void loginUser() {
  Log.d(TAG, mEmail + mPassword);
    mAuth.signInWithEmailAndPassword(mEmail, mPassword)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d(TAG, "signInWithEmail:success");
              FirebaseUser user = mAuth.getCurrentUser();
              openWelcomeActivity(user);
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signInWithEmail:failure", task.getException());
              Toast.makeText(SignInActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
//              updateUI(null);
            }
          }
        });
  }

  private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
    @Override
    public void run(AccountManagerFuture<Bundle> result) {
      // Get the result of the operation from the AccountManagerFuture.
      Bundle bundle = null;
      try {
        bundle = result.getResult();
      } catch (OperationCanceledException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (AuthenticatorException e) {
        e.printStackTrace();
      }

      // The token is a named value in the bundle. The name of the value
      // is stored in the constant AccountManager.KEY_AUTHTOKEN.
      if (bundle != null) {
        mPassword = bundle.getString("password");
        mEmail = bundle.getString("username");
        Log.d(TAG, " token");
        if(!mEmail.isEmpty() && !mPassword.isEmpty()){
          loginUser();
        }
      }
//        ...
    }
  }
}
