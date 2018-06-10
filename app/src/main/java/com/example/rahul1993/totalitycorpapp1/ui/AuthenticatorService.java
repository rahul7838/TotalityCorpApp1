package com.example.rahul1993.totalitycorpapp1.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rahul1993 on 6/8/2018.
 */

public class AuthenticatorService extends Service {
  private static final String TAG = AuthenticatorService.class.getSimpleName();
  Authenticator authenticator;
  private Context context;
  @Override
  public void onCreate() {
    super.onCreate();
    authenticator = new Authenticator(this);

  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "Authenticator service");
    return authenticator.getIBinder();
  }
}
