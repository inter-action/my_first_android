package com.example.macroid.ui.login

import scala.language.postfixOps

import android.text.InputType
import android.view.{Gravity, ViewGroup}
import android.view.ViewGroup.LayoutParams
import android.widget._
import android.support.design.widget._

import macroid.{Tweak, ActivityContextWrapper}
import macroid.FullDsl._
import macroid.IdGeneration

import com.example.macroid.ui.LinearLayoutTweaks
import com.example.macroid.ui.LinearLayoutTweaks._


import com.example.macroid.ui.ViewTweaks._

/**
  * Created by interaction on 6/14/16.
  */
trait Layout extends IdGeneration {
  /*
  Ids:
    Id.textEmailAddress
   */

  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      l[ScrollView](
        l[LinearLayout](
          w[ImageView] <~ Tweak[ImageView]{imageView =>
            imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 72 dp))
          } <~ llLayoutMargin(24 dp) <~ LinearLayoutTweaks.gravity(Gravity.CENTER_HORIZONTAL),

          l[TextInputLayout](
            w[EditText] <~ Tweak[EditText]{ view =>
              view.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
              view.setHint("Email")
              view.setId(Id.textEmailAddress)
            } <~ vMatchWidth
          ) <~ vMatchWidth <~ llLayoutMargin(0, 8 dp, 0, 8 dp)

        ) <~ vMatchWidth <~ padding(24 dp, 56 dp, 24 dp, -1)
      ) <~ vMatchParent <~ Tweak[ScrollView](_.setFitsSystemWindows(true))

    )
  }
}
