package com.example.macroid.ui


import android.view.{ViewGroup, View}

import android.view.ViewGroup.LayoutParams._
import android.widget.{LinearLayout, TextView}

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
    Tweak[W](_.setTextColor(context.application.getResources.getColor(resColor, null)))

}

object LinearLayoutTweaks {
  import android.widget.LinearLayout.LayoutParams

  type W = LinearLayout


  def llLayoutMargin[IW <: View](
              left: Int = 0,
              top: Int = 0,
              right: Int = 0,
              bottom: Int = 0 ): Tweak[IW] = Tweak[IW]{ view =>

    val params = new LayoutParams(view.getLayoutParams)
    params.setMargins(left, top, right, bottom)
    view.setLayoutParams(params)
  }


  def gravity[IW <: View](gravity: Int): Tweak[IW] = Tweak[IW] { view =>
    val params = new LayoutParams(view.getLayoutParams)
    params.gravity = gravity
    view.setLayoutParams(params)
  }
}