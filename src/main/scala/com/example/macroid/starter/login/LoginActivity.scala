package com.example.macroid.starter.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import macroid.Contexts

/**
  * Created by interaction on 6/14/16.
  */
class LoginActivity extends AppCompatActivity
  with Contexts[AppCompatActivity]
  with Layout{

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    this.setContentView(layout)

  }
}
