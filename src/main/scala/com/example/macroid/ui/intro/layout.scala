package com.example.macroid.ui.intro

import android.support.v4.view.ViewPager.OnPageChangeListener

import scala.language.postfixOps
import scala.util._

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

    var dots = Array(slot[Button], slot[Button], slot[Button], slot[Button], slot[Button])

    var pager = slot[ViewPager]

    var _line = slot[View]
    var _skip = slot[TextView]
    var _forward = slot[TextView]

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

    val pageChangeListener = new OnPageChangeListener {
      override def onPageScrollStateChanged(i: Int): Unit = ()

      override def onPageScrolled(i: Int, v: Float, i1: Int): Unit = ()


      override def onPageSelected(index: Int): Unit = {
        dots.zip(0 until dots.length).foreach[Unit]{ e: (Option[Button], Int) =>
          val (btn, idx) = e
          if (idx == index) btn.map(_.setPressed(true)) else btn.map(_.setPressed(false))
        }


        def toggleVisibility(status: Int) = {
          val groups = List(_line, _skip, _forward)
          groups.foreach(_.map[Unit](_.setVisibility(status)))
        }

        if (index != 4){
          toggleVisibility(View.VISIBLE)
        } else {
          toggleVisibility(View.GONE)
        }
      }
    }


    def layout(implicit context: ActivityContextWrapper, fragmentManagerContext: FragmentManagerContext[Fragment, FragmentManager]) = {
      getUi(
        l[FrameLayout](
          l[ViewPager]() <~ vMatchParent
            <~ vpAdapter(new IntroPageAdapter(fragmentManagerContext.get, List("Page1", "Page2", "Page3", "Page4", "Page5")))
            <~ id(Id.WALK_TROUGH_PAGER) // ViewPager's Id has to be set, or error will throw at runtime
            <~ vpOnPageChangeListener(pageChangeListener)
            <~ wire(pager),

          l[LinearLayout](
            w[View] <~ vBackgroundColorResource(R.color.iron)
              <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 1 dp) <~ wire(_line),

            l[RelativeLayout](
              w[TextView]
                <~ tvText("Skip") <~ tvStyle(R.style.walkthrough_font)
                <~ vWrapContent <~ toRelativeLayout
                <~ rlAlignParent(left = true, centerVertical = true) <~ wire(_skip),

              l[LinearLayout](
                dot <~ Tweak[Button]{btn=> dots(0) = Some(btn)} <~ btPressed(true),
                // todo: wire(dots(0)) just wont compile successfully
                dot <~ Tweak[Button]{btn=> dots(1) = Some(btn)},
                dot <~ Tweak[Button]{btn=> dots(2) = Some(btn)},
                dot <~ Tweak[Button]{btn=> dots(3) = Some(btn)},
                dot <~ Tweak[Button]{btn=> dots(4) = Some(btn)}
              ) <~ vWrapContent
                <~ toRelativeLayout <~ rlAlignParent(center = true),

              w[TextView] <~ tvText("forward") <~ tvStyle(R.style.walkthrough_font)
                <~ vWrapContent <~ toRelativeLayout
                <~ rlAlignParent(centerVertical = true, right = true)
                <~ wire(_forward)
                <~ On.click( Ui {
                  pager.map { p =>
                    val cur = p.getCurrentItem
                    if (cur < p.getAdapter.getCount){
                      p.setCurrentItem(cur+1, true)
                    }
                  }
                })
            ) <~ vSize(ViewGroup.LayoutParams.MATCH_PARENT, 63.toDpr dp)
              <~ vPaddings(16.toDpr dp)
          ) <~ vMatchWidth
            <~ llVertical
            <~ flGravity(Gravity.BOTTOM)
            <~ vBackgroundColorResource(R.color.white)
        )
      )
    }

  }
}

