package com.example.rahul1993.totalitycorpapp1.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rahul1993 on 6/8/2018.
 */

public class Utils {

  public static boolean checkEmailValidity(String email){
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return matcher.find();
  }

//  public static boolean checkPasswordValidity(String password){
//    String
//  }
}
