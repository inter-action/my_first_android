package com.example.macroid.ui.register


import android.text.InputType
import android.view.{View, Gravity, ViewGroup}
import android.view.ViewGroup.{MarginLayoutParams, LayoutParams}
import android.widget._
import android.support.design.widget._
import android.support.v7.widget.AppCompatButton
import android.util.Log

import macroid.{Tweak, ActivityContextWrapper}
import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts
import macroid.IdGeneration

import com.example.macroid.ui.LinearLayoutTweaks._

import com.example.macroid.ui.TextTweak._
import com.example.macroid.ui.ViewTweaks._
import com.example.macroid.ui.EditTextTweaks._
import com.example.macroid.ui.R

trait Layout extends IdGeneration{

  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      w[TextView] <~ tvText("register")
    )
  }
}