package com.example.macroid.ui.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import macroid.Contexts


class RegisterActivity extends AppCompatActivity
  with Contexts[AppCompatActivity]
  with Layout{

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    //    this.setContentView(R.layout.activity_login)
    this.setContentView(layout)
  }
}