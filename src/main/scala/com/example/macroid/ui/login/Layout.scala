package com.example.macroid.ui.login

import scala.language.postfixOps

import android.text.InputType
import android.view.{View, Gravity, ViewGroup}
import android.view.ViewGroup.{MarginLayoutParams, LayoutParams}
import android.widget._
import android.support.design.widget._
import android.support.v7.widget.AppCompatButton
import android.util.Log

import macroid.{Tweak, ActivityContextWrapper}
import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts
import macroid.IdGeneration

import com.example.macroid.ui.LinearLayoutTweaks._

import com.example.macroid.ui.TextTweak._
import com.example.macroid.ui.ViewTweaks._
import com.example.macroid.ui.EditTextTweaks._
import com.example.macroid.ui.R
/**
  * Created by interaction on 6/14/16.
  *
  * this layout is identical at res/layout/acitivity_login
  */
trait Layout extends IdGeneration {
  /*
  Ids:
    Id.textEmailAddress
   */
  val tag = "LoginLayout"

  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      l[ScrollView](
        l[LinearLayout](
          l[LinearLayout](
            // bm: dont know why but it has to be wrapped in a Layout for margin to work
            w[ImageView] <~ Tweak[ImageView]{imageView =>
              val params = new LayoutParams(LayoutParams.WRAP_CONTENT, 72 dp)
              imageView.setLayoutParams(params)
              imageView.setBackground(context.application.getDrawable(R.drawable.logo))
            } <~ gravity(Gravity.CENTER)
          ) <~ vMatchWidth <~ llLayoutMargin(top = 24 dp, bottom = 24 dp),

          // Email Input
          l[TextInputLayout](
            w[EditText] <~ Tweak[EditText]{ view =>
              view.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
              view.setHint("Email")
              view.setId(Id.textEmailAddress)
              Log.d(tag, "initial email")

            } <~ vMatchWidth
          ) <~ vMatchWidth <~ llLayoutMargin(0, 8 dp, 0, 8 dp),


          l[TextInputLayout](
            w[EditText] <~ Tweak[EditText]{ view =>
              view.setHint("Password")
              view.setId(Id.textPassword)
              Log.d(tag, "initial password")
            } <~ vMatchWidth <~ typePassword
          ) <~ vMatchWidth <~ llLayoutMargin(0, 8 dp, 0, 8 dp),
          /*
            <android.support.v7.widget.AppCompatButton
            android:id="@+id/btn_login"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:layout_marginBottom="24dp"
            android:padding="12dp"
            android:text="Login"/>
           */
          w[AppCompatButton] <~ vMatchWidth <~ padding(all = 12 dp) <~
            llLayoutMargin(top = 24 dp, bottom = 24 dp) <~ id(Id.btnLogin) <~ tvText("Login"),


          /*
          <TextView android:id="@+id/link_signup"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="24dp"
            android:text="No account yet? Create one"
            android:gravity="center"
            android:textSize="16dip"/>
           */

          w[TextView] <~ vWrapContent
            <~ tvText("No account yet? Create one")
            <~ llLayoutMargin(bottom = 24 dp) <~ tvSizePiexls(16 dp)
            <~ tvCenter
        ) <~ vMatchWidth <~ padding(24 dp, 56 dp, 24 dp, -1) <~ llVertical
      ) <~ vMatchParent <~ Tweak[ScrollView](_.setFitsSystemWindows(true))

    )
  }
}
