package com.example.macroid.ui


import android.text.InputType
import android.util.TypedValue
import android.view.ViewGroup.{MarginLayoutParams, LayoutParams}
import android.view.{Gravity, ViewGroup, View}

import android.view.ViewGroup.LayoutParams._
import android.widget.{EditText, LinearLayout, TextView}

import macroid.{Ui, ContextWrapper, Tweak}
import macroid.FullDsl._

object ViewTweaks {
  private type W = View

  private def lp(w: Int, h: Int): Tweak[W] = Tweak[W](_.setLayoutParams(new LayoutParams(w, h)))
  val vMatchParent: Tweak[W] = lp(MATCH_PARENT, MATCH_PARENT)
  val vWrapContent: Tweak[W] = lp(WRAP_CONTENT, WRAP_CONTENT)
  val vMatchWidth: Tweak[W] = lp(MATCH_PARENT, WRAP_CONTENT)
  val vMatchHeight: Tweak[W] = lp(WRAP_CONTENT, MATCH_PARENT)

  // sets view's margin, not layout
  def vMargins(margin: Int): Tweak[W] = Tweak[W] { view =>
    view.getLayoutParams
      .asInstanceOf[ViewGroup.MarginLayoutParams]
      .setMargins(margin, margin, margin, margin)
    view.requestLayout()
  }

  def vMargin(
               left: Int = 0,
               top: Int = 0,
               right: Int = 0,
               bottom: Int = 0
             ): Tweak[W] = Tweak[W] { view =>
    view
      .getLayoutParams
      .asInstanceOf[ViewGroup.MarginLayoutParams]
      .setMargins(left, top, right, bottom)
    view.requestLayout()
  }

  val toMarginLayout = Tweak[W]{view=>
    view.setLayoutParams(new MarginLayoutParams(view.getLayoutParams))
  }
}


object TextTweak {
  private type W = TextView

  def tvColor(color: Int): Tweak[W] = Tweak[W](_.setTextColor(color))

  def tvColorRes(resColor: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setTextColor(context.application.getResources.getColor(resColor, null)))

  def tvText(text: CharSequence): Tweak[W] = Tweak[W](_.setText(text))
  def tvText(res: Int): Tweak[W] = Tweak[W](_.setText(res))

  def tvSize(points: Int): Tweak[W] = Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_SP, points))
  def tvSizePiexls(pixels: Int): Tweak[W] = Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels))
  def tvSizeResource(res: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.application.getResources.getDimension(res)))

  def tvAlignment(align: Int) = Tweak[W](_.setTextAlignment(align))

  val tvCenter = LinearLayoutTweaks.gravity(Gravity.CENTER)
}

object LinearLayoutTweaks {
  import android.widget.LinearLayout

  private type W = LinearLayout

  val llHorizontal: Tweak[W] = Tweak[W](_.setOrientation(LinearLayout.HORIZONTAL))

  val llVertical: Tweak[W] = Tweak[W](_.setOrientation(LinearLayout.VERTICAL))

  // bm: 这个 IW 限制没有必要, Tweaks 串联的时候 Widget 的type 不会发生任何变化
  // def llLayoutMargin[IW <: View](
  def llLayoutMargin(
              left: Int = 0,
              top: Int = 0,
              right: Int = 0,
              bottom: Int = 0 ): Tweak[View] = Tweak[View]{ view =>
    val params = new LinearLayout.LayoutParams(view.getLayoutParams)
    params.setMargins(left, top, right, bottom)
    view.setLayoutParams(params)
  }


  def gravity(gravity: Int): Tweak[View] = Tweak[View] { view: View =>
    val params = new LinearLayout.LayoutParams(view.getLayoutParams)
    params.gravity = gravity
    view.setLayoutParams(params)
  }

}


object EditTextTweaks {
  private type W = EditText

  // @see : http://stackoverflow.com/questions/9892617/programmatically-change-input-type-of-the-edittext-from-password-to-normal-vic
  val typePassword = Tweak[W](_.setInputType(
    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD))


}