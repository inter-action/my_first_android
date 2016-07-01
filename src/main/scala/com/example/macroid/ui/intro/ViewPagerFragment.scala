package com.example.macroid.ui.intro

import android.graphics.Color

import scala.language.postfixOps

import android.os.Bundle
import android.support.v4.app.{FragmentPagerAdapter, FragmentManager, Fragment}
import android.view.WindowManager.LayoutParams
import android.view.{Gravity, View, ViewGroup, LayoutInflater}
import android.widget._
import macroid._

import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts

import com.example.macroid.ui.TextTweak._
import com.example.macroid.ui.ViewTweaks._
import com.example.macroid.ui.EditTextTweaks._
import com.example.macroid.ui.ViewPagerTweaks._
import com.example.macroid.ui.Utils._
import com.example.macroid.ui.LinearLayoutTweaks._
import com.example.macroid.ui.FrameLayoutTweaks._
import com.example.macroid.ui.RelativeLayoutTweaks._
import com.example.macroid.ui.ButtonTweaks._
import com.example.macroid.ui.R

import ViewPagerFragment._

class ViewPagerFragment(implicit context: ActivityContextWrapper) extends Fragment {
  lazy val text: String = getArguments.getString(TEXT)

  /*
  View.setPressed(boolean)
   */
  def dot:Ui[Button] = {
    w[Button] <~ (
      vSize(5.toDpr dp, 5.toDpr dp) + llGravity(Gravity.CENTER_VERTICAL)
        + vOpMargin(left = Some(1 dp), right = Some(1 dp))
        + vBackground(R.drawable.walkthrough_points)
    )
  }


  override def onCreateView(
                             inflater: LayoutInflater,
                             container: ViewGroup,
                             savedInstanceState: Bundle): View = getUi {
    l[FrameLayout](
      l[FrameLayout](
        w[View]
          <~ vSize(292.toDpr dp, 589.toDpr dp)
          <~ flGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL)
          <~ vBackground(R.drawable.res_walkthrough_phone1)
      ) <~ vMatchParent <~ vBackgroundColorResource(R.color.primary),

      //bottom section
      l[LinearLayout](
        l[LinearLayout](
          w[TextView]
            <~ tvText("Award Winning Content ")
            <~ tvColor(R.color.black)
            <~ tvSize(23)
            <~ vWrapContent
            <~ llGravity(Gravity.CENTER_HORIZONTAL),
          w[TextView]
            <~ tvText("Secure container separates business and personal data with end to end encryption and managed security policies.")
            <~ tvColor(R.color.black)
            <~ tvSize(13)
            <~ vSize(272.toDpr dp, ViewGroup.LayoutParams.WRAP_CONTENT)
            <~ llGravity(Gravity.CENTER_HORIZONTAL)
            <~ vOpMargin(top = Some(16.toDpr dp))
        ) <~ vMatchWidth
          <~ llVertical
          <~ llWeight(1)
          <~ vPaddings(0, 27.toDpr dp)
          <~ vBackgroundColorResource(R.color.white),

        w[View] <~ vBackgroundColorResource(R.color.iron) <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 1 dp),
        l[RelativeLayout](
          w[TextView]
            <~ tvText("Skip") <~ tvStyle(R.style.walkthrough_font)
            <~ vWrapContent <~ toRelativeLayout
            <~ rlAlignParent(left = true, centerVertical = true),

          l[LinearLayout](
            dot <~ btPressed(true), dot, dot, dot
          ) <~ vWrapContent
            <~ toRelativeLayout <~ rlAlignParent(center = true),

          w[TextView] <~ tvText("forward") <~ tvStyle(R.style.walkthrough_font)
            <~ vWrapContent <~ toRelativeLayout
            <~ rlAlignParent(centerVertical = true, right = true)
        ) <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 63.toDpr dp)
          <~ vPaddings(16.toDpr dp)
      ) <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 227.toDpr dp)
        <~ llVertical
        <~ flGravity(Gravity.BOTTOM)
        <~ vBackgroundColorResource(R.color.white)
    )
  }
}

object ViewPagerFragment {
  val TEXT = "STRING"
}

class IntroPageAdapter(val fragmentManager: FragmentManager, items: List[String])
                      (implicit context: ActivityContextWrapper)
  extends FragmentPagerAdapter(fragmentManager) {

  override def getItem(i: Int): Fragment = {
    val fg = new ViewPagerFragment()
    val bundle = new Bundle()
    bundle.putString(TEXT, items(i))
    fg.setArguments(bundle)
    fg
  }

  override def getCount: Int = items.length
}