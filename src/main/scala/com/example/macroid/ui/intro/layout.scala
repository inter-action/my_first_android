package com.example.macroid.ui.intro

import android.support.v4.app.{Fragment, FragmentManager, FragmentPagerAdapter}



class IntroPageAdapter(val fragmentManager: FragmentManager)
  extends FragmentPagerAdapter(fragmentManager) {

  override def getItem(i: Int): Fragment = ???

  override def getCount: Int = 4
}


object Layouts {
  import android.widget._


  import macroid.{Ui, Tweak, ActivityContextWrapper, IdGeneration}
  import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts

  import com.example.macroid.ui.LinearLayoutTweaks._

  import com.example.macroid.ui.TextTweak._
  import com.example.macroid.ui.ViewTweaks._
  import com.example.macroid.ui.EditTextTweaks._
  import com.example.macroid.ui.R


  trait WalkThroughLayout {

    def layout(implicit context: ActivityContextWrapper) = {
      getUi(
        l[LinearLayout](
          w[TextView] <~ tvText("intro")
        )

      )
    }

  }
}

