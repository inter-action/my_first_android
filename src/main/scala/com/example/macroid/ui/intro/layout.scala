package com.example.macroid.ui.intro

import scala.language.postfixOps

import android.widget._
import android.support.v4.app.{Fragment, FragmentManager, FragmentPagerAdapter}
import android.support.v4.view.ViewPager
import android.view.{Gravity, View, ViewGroup, LayoutInflater}

import macroid.FragmentManagerContext
import macroid._
import macroid.{Ui, Tweak, ActivityContextWrapper, IdGeneration}
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

object Layouts {

  trait WalkThroughLayout extends IdGeneration{

    /*
    View.setPressed(boolean)
     */
    def dot(implicit ctx: ContextWrapper):Ui[Button] = {
      w[Button] <~ (
        vSize(5.toDpr dp, 5.toDpr dp) + llGravity(Gravity.CENTER_VERTICAL)
          + vOpMargin(left = Some(1 dp), right = Some(1 dp))
          + vBackground(R.drawable.walkthrough_points)
        )
    }

    def layout(implicit context: ActivityContextWrapper, fragmentManagerContext: FragmentManagerContext[Fragment, FragmentManager]) = {
      getUi(
        l[FrameLayout](
          l[ViewPager]() <~ vMatchParent
            <~ vpAdapter(new IntroPageAdapter(fragmentManagerContext.get, List("Page1", "Page2", "Page3", "Page4")))
            <~ id(Id.WALK_TROUGH_PAGER), // ViewPager's Id has to be set, or error will throw at runtime


          l[LinearLayout](
            w[View] <~ vBackgroundColorResource(R.color.iron)
              <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 1 dp),

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
          ) <~ vMatchWidth
            <~ flGravity(Gravity.BOTTOM)
            <~ vBackgroundColorResource(R.color.white)
        )
      )
    }

  }
}

