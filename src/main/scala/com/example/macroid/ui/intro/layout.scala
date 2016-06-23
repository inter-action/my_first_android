package com.example.macroid.ui.intro

import android.os.Bundle
import android.support.v4.app.{Fragment, FragmentManager, FragmentPagerAdapter}
import android.support.v4.view.ViewPager
import android.view.{View, ViewGroup, LayoutInflater}
import macroid.FragmentManagerContext
import macroid.support.FragmentApi






object Layouts {
  import android.widget._

  import macroid.{Ui, Tweak, ActivityContextWrapper, IdGeneration}
  import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts

  import com.example.macroid.ui.LinearLayoutTweaks._

  import com.example.macroid.ui.TextTweak._
  import com.example.macroid.ui.ViewTweaks._
  import com.example.macroid.ui.EditTextTweaks._
  import com.example.macroid.ui.ViewPagerTweaks._
  import com.example.macroid.ui.R


  trait WalkThroughLayout extends IdGeneration{

    def layout(implicit context: ActivityContextWrapper, fragmentManagerContext: FragmentManagerContext[Fragment, FragmentManager]) = {
      getUi(
        l[ViewPager]( ) <~ vMatchParent
          <~ vpAdapter(new IntroPageAdapter(fragmentManagerContext.get, List("Page1", "Page2")))
          <~ id(Id.WALK_TROUGH_PAGER) // ViewPager's Id has to be set, or error will throw at runtime
      )
    }

  }
}

