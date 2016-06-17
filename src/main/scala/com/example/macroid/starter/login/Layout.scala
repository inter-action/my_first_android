package com.example.macroid.starter.login

import android.widget.{LinearLayout, TextView}
import macroid.ActivityContextWrapper
import macroid.FullDsl._

/**
  * Created by interaction on 6/14/16.
  */
trait Layout {
  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      l[LinearLayout](
        w[TextView] <~ text("Login Pannel")
      )
    )
  }
}
