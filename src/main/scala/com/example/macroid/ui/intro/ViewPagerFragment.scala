package com.example.macroid.ui.intro

import android.os.Bundle
import android.support.v4.app.{FragmentPagerAdapter, FragmentManager, Fragment}
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.TextView
import macroid.{FragmentManagerContext, IdGeneration, ActivityContextWrapper}

import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts

import com.example.macroid.ui.TextTweak._
import com.example.macroid.ui.ViewTweaks._
import com.example.macroid.ui.EditTextTweaks._
import com.example.macroid.ui.ViewPagerTweaks._
import com.example.macroid.ui.R

import ViewPagerFragment._

class ViewPagerFragment(implicit context: ActivityContextWrapper) extends Fragment {
  lazy val text: String = getArguments.getString(TEXT)
  override def onCreateView(
                             inflater: LayoutInflater,
                             container: ViewGroup,
                             savedInstanceState: Bundle): View = getUi {
    w[TextView] <~ tvText(text)
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