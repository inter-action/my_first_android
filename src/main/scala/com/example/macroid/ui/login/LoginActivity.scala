package com.example.macroid.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import macroid.Contexts

import com.example.macroid.ui.R
/**
  * Created by interaction on 6/14/16.
  */
class LoginActivity extends AppCompatActivity
  with Contexts[AppCompatActivity]
  with Layout{

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

//    this.setContentView(R.layout.activity_login)
    this.setContentView(layout)
  }
}
