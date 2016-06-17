package com.example.macroid.ui


import android.view.{ViewGroup, View}

import android.view.ViewGroup.LayoutParams._
import android.widget.TextView

import macroid.{Ui, ContextWrapper, Tweak}
import macroid.FullDsl._

object ViewTweaks {
  type W = View

  val vMatchParent: Tweak[W] = lp[ViewGroup](MATCH_PARENT, MATCH_PARENT)
  val vWrapContent: Tweak[W] = lp[ViewGroup](WRAP_CONTENT, WRAP_CONTENT)
  val vMatchWidth: Tweak[W] = lp[ViewGroup](MATCH_PARENT, WRAP_CONTENT)
  val vMatchHeight: Tweak[W] = lp[ViewGroup](WRAP_CONTENT, MATCH_PARENT)
}


object TextTweak {
  type W = TextView

  def tvColor(color: Int): Tweak[W] = Tweak[W](_.setTextColor(color))

  def tvColorRes(resColor: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setTextColor(context.application.getResources.getColor(resColor)))

}