package com.example.rahul1993.totalitycorpapp1.ui;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by rahul1993 on 6/8/2018.
 */

public class Authenticator extends AbstractAccountAuthenticator {
  private static final String TAG = Authenticator.class.getSimpleName();
  private Context context;
  public Authenticator(Context context) {
    super(context);
    this.context = context;
  }

  @Override
  public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
    Log.d(TAG, "editProperties");
    return null;
  }

  @Override
  public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
//    Bundle bundle = new Bundle();
    Log.d(TAG, "addAccount");

    return null;
  }

  @Override
  public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
    Log.d(TAG, "confirmCredentials");

    return null;

  }

  @Override
  public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType,
                             Bundle options) throws NetworkErrorException {
    Log.d(TAG, "getAuthToken");
//
    AccountManager accountManager = AccountManager.get(context);

    String authToken = accountManager.peekAuthToken(account, authTokenType);
    String password = null;
    String username = null;
    if(TextUtils.isEmpty(authToken)){
      password = accountManager.getPassword(account);
      username = account.name;
    }

    Bundle bundle = new Bundle();
    bundle.putString("password", password);
    bundle.putString("username", username);
    return bundle;

  }

  @Override
  public String getAuthTokenLabel(String authTokenType) {
    Log.d(TAG, "getAuthTokenLabel");

    return null;
  }

  @Override
  public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
    Log.d(TAG, "updateCredentials");

    return null;
  }

  @Override
  public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
    Log.d(TAG, "hasFeature");

    return null;
  }
}
