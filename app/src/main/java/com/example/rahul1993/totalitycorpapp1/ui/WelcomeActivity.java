package com.example.rahul1993.totalitycorpapp1.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rahul1993.totalitycorpapp1.R;
import com.example.rahul1993.totalitycorpapp1.databinding.ActivityWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by rahul1993 on 6/8/2018.
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
  ActivityWelcomeBinding mActivityWelcomeBinding;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
    mActivityWelcomeBinding.logoutId.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    FirebaseAuth.getInstance().signOut();
    Intent intent = new Intent(this, SignInActivity.class);
    startActivity(intent);
  }
}
