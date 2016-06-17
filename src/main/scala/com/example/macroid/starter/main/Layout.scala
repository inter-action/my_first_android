package com.example.macroid.starter.main

import android.widget.{LinearLayout, TextView}
import macroid.ActivityContextWrapper
import macroid.FullDsl._

import com.example.macroid.starter.R

import com.example.macroid.ui.TextTweak._
/**
  * Created by interaction on 6/14/16.
  */
trait Layout {
  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      l[LinearLayout](
        w[TextView] <~ text("Login Pannel") <~ tvColorRes(R.color.ascent)
      )
    )
  }
}
