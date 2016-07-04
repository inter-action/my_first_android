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
  lazy val index = getArguments.getInt(INDEX)


  /*
  "Award Winning Content "
   */
  def baseContent(background: Int, bkgColor: Int, heading: String, desc: String) = getUi {
    l[FrameLayout](
      l[FrameLayout](
        w[View]
          <~ vSize(292.toDpr dp, 589.toDpr dp)
          <~ flGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL)
          <~ vBackground(background)
      ) <~ vMatchParent <~ vBackgroundColor(bkgColor),

      //bottom section
      l[LinearLayout](
        w[TextView]
          <~ tvText(heading)
          <~ tvColor(R.color.black)
          <~ tvSize(23)
          <~ vWrapContent
          <~ llGravity(Gravity.CENTER_HORIZONTAL),
        w[TextView]
          <~ tvText(desc)
          <~ tvColor(R.color.black)
          <~ tvSize(13)
          <~ vSize(272.toDpr dp, ViewGroup.LayoutParams.WRAP_CONTENT)
          <~ llGravity(Gravity.CENTER_HORIZONTAL)
          <~ vOpMargin(top = Some(16.toDpr dp))
      ) <~ vMatchWidth
        <~ llVertical
        <~ vPaddings(0, 27.toDpr dp)
        <~ vBackgroundColorResource(R.color.white)
        <~ toFrameLayout
        <~ flGravity(Gravity.BOTTOM)
        //note, for FrameLayout Its gravity has to be set first before margin to take effect
        // http://stackoverflow.com/questions/5401952/framelayout-margin-not-working

        <~ vOpMargin(bottom = Some( (63.toDpr  + 1) dp))
    ) <~ vMatchParent
  }



  override def onCreateView(
                             inflater: LayoutInflater,
                             container: ViewGroup,
                             savedInstanceState: Bundle): View = index match {
    case 0 =>
      val bkgColor = Color.parseColor("#C3372F")
      val heading = "Award Winning Content "
      val desc = "Secure container separates business and personal data with end to end encryption and managed security policies."
      baseContent(R.drawable.res_walkthrough_phone1, bkgColor, heading, desc)
    case 1 =>
      val bkgColor = Color.parseColor("#502FAB")
      val heading = "Flexible Monthly Access"
      val desc = "Free access to Microsoft hosted exchange email, PIM and create / edit Powerpoint, Excel, Word and PDFs! "
      baseContent(R.drawable.res_walkthrough_phone2, bkgColor, heading, desc)
    case 2 =>
      val bkgColor = Color.parseColor("#508BEB")
      val heading = "Choose from Categories"
      val desc = "Securely record & store encrypted photo / video, browse the web & access cloud storage and shared spaces"
      baseContent(R.drawable.res_walkthrough_phone3, bkgColor, heading, desc)
    case 3 =>
      val bkgColor = Color.parseColor("#DB5413")
      val heading = "Hassle Free Installations"
      val desc = "Enterprise application development & central management coupled with secure integration of enterprise systems."
      baseContent(R.drawable.res_walkthrough_phone4, bkgColor, heading, desc)
  }
}

object ViewPagerFragment {
  val TEXT = "TEXT"
  val INDEX = "INDEX"
}

class IntroPageAdapter(val fragmentManager: FragmentManager, items: List[String])
                      (implicit context: ActivityContextWrapper)
  extends FragmentPagerAdapter(fragmentManager) {

  override def getItem(i: Int): Fragment = {
    val fg = new ViewPagerFragment()
    val bundle = new Bundle()
    bundle.putString(TEXT, items(i))
    bundle.putInt(INDEX, i)
    fg.setArguments(bundle)
    fg
  }

  override def getCount: Int = items.length
}