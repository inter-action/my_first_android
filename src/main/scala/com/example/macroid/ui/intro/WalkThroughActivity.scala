package com.example.macroid.ui.intro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.macroid.ui.login.Layout
import macroid.Contexts
import macroid.support.FragmentApi._

class WalkThroughActivity extends AppCompatActivity
  with Contexts[AppCompatActivity]
  with Layouts.WalkThroughLayout
  {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    //    this.setContentView(R.layout.activity_login)
    this.setContentView(layout)
  }
}
