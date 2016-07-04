package com.example.macroid.ui


import android.support.v4.content.ContextCompat
import android.support.v4.view.{PagerAdapter, ViewPager}
import android.text.InputType
import android.util.TypedValue
import android.view.ViewGroup.{MarginLayoutParams, LayoutParams}
import android.view.{Gravity, ViewGroup, View}

import android.view.ViewGroup.LayoutParams._
import android.widget._

import macroid.{Ui, ContextWrapper, Tweak}
import macroid.FullDsl._

object ViewTweaks {
  private type W = View

  private def lp(w: Int, h: Int): Tweak[W] = Tweak[W](_.setLayoutParams(new LayoutParams(w, h)))
  val vMatchParent: Tweak[W] = lp(MATCH_PARENT, MATCH_PARENT)
  val vWrapContent: Tweak[W] = lp(WRAP_CONTENT, WRAP_CONTENT)
  val vMatchWidth: Tweak[W] = lp(MATCH_PARENT, WRAP_CONTENT)
  val vMatchHeight: Tweak[W] = lp(WRAP_CONTENT, MATCH_PARENT)
  val vSize: (Int, Int)=>Tweak[W] = lp(_, _)
  // sets view's margin, not layout
  def vMargins(margin: Int): Tweak[W] = Tweak[W] { view =>
    view.getLayoutParams
      .asInstanceOf[ViewGroup.MarginLayoutParams]
      .setMargins(margin, margin, margin, margin)
    view.requestLayout()
  }

  def vBackground(res: Int): Tweak[W] = Tweak[W](_.setBackgroundResource(res))

  def vBackgroundColor(color: Int): Tweak[W] = Tweak[W](_.setBackgroundColor(color))

  def vBackgroundColorResource(colorRes: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setBackgroundColor(ContextCompat.getColor(context.getOriginal, colorRes)))


  def vMargin(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0): Tweak[W] = Tweak[W] { view =>
    view
      .getLayoutParams
      .asInstanceOf[ViewGroup.MarginLayoutParams]
      .setMargins(left, top, right, bottom)
    view.requestLayout()
  }

  def vOpMargin(
    left: Option[Int] = Option.empty,
    top: Option[Int] = Option.empty,
    right: Option[Int] = Option.empty,
    bottom: Option[Int] = Option.empty) = Tweak[W]{ view =>


    val params = view.getLayoutParams.asInstanceOf[ViewGroup.MarginLayoutParams]
    left.map( params.leftMargin = _)
    top.map( params.topMargin = _)
    right.map( params.rightMargin = _)
    bottom.map( params.bottomMargin = _)

    view.requestLayout()
  }


  def vPaddings(padding: Int): Tweak[W] = Tweak[W](_.setPadding(padding, padding, padding, padding))

  def vPaddings(
    paddingLeftRight: Int = 0,
    paddingTopBottom: Int = 0): Tweak[W] =
    Tweak[W](_.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom))

  def vPadding(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0): Tweak[W] =
    Tweak[W](_.setPadding(left, top, right, bottom))

}


object TextTweak {
  private type W = TextView

  def tvColor(color: Int): Tweak[W] = Tweak[W](_.setTextColor(color))

  def tvColorRes(resColor: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setTextColor(ContextCompat.getColor(context.getOriginal, resColor)))

  def tvText(text: CharSequence): Tweak[W] = Tweak[W](_.setText(text))
  def tvText(res: Int): Tweak[W] = Tweak[W](_.setText(res))

  def tvSize(points: Int): Tweak[W] = Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_SP, points))
  def tvSizePiexls(pixels: Int): Tweak[W] = Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels))
  def tvSizeResource(res: Int)(implicit context: ContextWrapper): Tweak[W] =
    Tweak[W](_.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.application.getResources.getDimension(res)))

  def tvAlignment(align: Int) = Tweak[W](_.setTextAlignment(align))

  def tvGravity(gravity: Int) = Tweak[W](_.setGravity(gravity))

  def tvInputType(tvType: Int) = Tweak[W](_.setInputType(tvType))

  val TYPE_EMAIL = tvInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
  // @see : http://stackoverflow.com/questions/9892617/programmatically-change-input-type-of-the-edittext-from-password-to-normal-vic
  val TYPE_PASSWORD = tvInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)


  def tvStyle(style: Int)(implicit context: ContextWrapper) =
    Tweak[W]( _.setTextAppearance(context.getOriginal, style) )
}


object ButtonTweaks {
  private type W = Button

  def btPressed(boolean: Boolean) = Tweak[W]( _.setPressed(boolean) )
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


  def llGravity(gravity: Int): Tweak[View] = Tweak[View] { view: View =>
    val params = new LinearLayout.LayoutParams(view.getLayoutParams)
    params.gravity = gravity
    view.setLayoutParams(params)
  }

  def llWeight(weight: Int) = Tweak[View]{view=>
    val params = new LinearLayout.LayoutParams(view.getLayoutParams)
    params.weight = weight
    view.setLayoutParams(params)
  }

}

object FrameLayoutTweaks {
  def flGravity(gravity: Int) = Tweak[View] { view: View =>
    val params = new FrameLayout.LayoutParams(view.getLayoutParams)
    params.gravity = gravity
    view.setLayoutParams(params)
  }
}


object RelativeLayoutTweaks {
  private type W = View

  def rlAlignParent(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false,
    centerHorizontal: Boolean = false,
    centerVertical: Boolean = false,
    center: Boolean = false) = Tweak[W]{ view =>

    val params = view.getLayoutParams.asInstanceOf[RelativeLayout.LayoutParams]

    if (left) params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
    if (top) params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
    if (right) params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    if (bottom) params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    if (centerHorizontal) params.addRule(RelativeLayout.CENTER_HORIZONTAL)
    if (centerVertical) params.addRule(RelativeLayout.CENTER_VERTICAL)
    if (center) params.addRule(RelativeLayout.CENTER_IN_PARENT)

    view.setLayoutParams(params)
  }


  def toRelativeLayout = Tweak[W]{ view =>
    val params = view.getLayoutParams
    view.setLayoutParams(new RelativeLayout.LayoutParams(params))
  }
}


object EditTextTweaks {
  private type W = EditText
}


object ViewPagerTweaks {
  private type W = ViewPager


  def vpAdapter(pagerAdapter: PagerAdapter): Tweak[W] = Tweak[W](_.setAdapter(pagerAdapter))

}

object Utils {
  implicit class NumberPump(x: Int){
    // 1.125 = $DESIGN_WIDTH / 320 (which is the screen width with 160 dpi)
    def toDpr :Int = Math.floor(x / 1.125).toInt
  }
}