package com.example.macroid.ui.register


import scala.language.postfixOps

import android.text.InputType
import android.view.{View, Gravity, ViewGroup}
import android.view.ViewGroup.{MarginLayoutParams, LayoutParams}
import android.widget._
import android.support.design.widget._
import android.support.v7.widget.AppCompatButton
import android.util.Log


import macroid.{Ui, Tweak, ActivityContextWrapper, IdGeneration}
import macroid.FullDsl.{gravity => lGravity, _}//fix gravity conflicts

import com.example.macroid.ui.LinearLayoutTweaks._

import com.example.macroid.ui.TextTweak._
import com.example.macroid.ui.ViewTweaks._
import com.example.macroid.ui.EditTextTweaks._
import com.example.macroid.ui.R

import com.example.macroid.ui.login.CommonStyle

trait Layout extends IdGeneration{

  /*
<!--  Name Label -->
        <android.support.design.widget.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp">
            <EditText android:id="@+id/input_name"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textCapWords"
                android:hint="Name" />
        </android.support.design.widget.TextInputLayout>

        <!-- Email Label -->
        <android.support.design.widget.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp">
            <EditText android:id="@+id/input_email"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textEmailAddress"
                android:hint="Email" />
        </android.support.design.widget.TextInputLayout>

        <!-- Password Label -->
        <android.support.design.widget.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp">
            <EditText android:id="@+id/input_password"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textPassword"
                android:hint="Password"/>
        </android.support.design.widget.TextInputLayout>

        <!-- Signup Button -->
        <android.support.v7.widget.AppCompatButton
            android:id="@+id/btn_signup"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:layout_marginBottom="24dp"
            android:padding="12dp"
            android:text="Create Account"/>

        <TextView android:id="@+id/link_login"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="24dp"
            android:text="Already a member? Login"
            android:gravity="center"
            android:textSize="16dip"/>

 */

  def textInputLayoutStyle(implicit context: ActivityContextWrapper) =
    vMatchWidth + llLayoutMargin(top = 8 dp, bottom = 8 dp)
  def layout(implicit context: ActivityContextWrapper) = {
    def textInput(ipt: Ui[EditText]) =
      l[TextInputLayout](ipt) <~ vMatchWidth + llLayoutMargin(top = 8 dp, bottom = 8 dp)


    getUi(
      CommonStyle.CommonView(
        CommonStyle.Logo,

        textInput(
          w[EditText] <~ id(Id.nameIpt) <~ hint("Name") <~ vMatchWidth
            <~ tvInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        ),


        textInput(
          w[EditText] <~ id(Id.emailIpt) <~ hint("Email") <~ vMatchWidth
            <~ TYPE_EMAIL
        ),


        textInput(
          w[EditText] <~ id(Id.passwordIpt) <~ hint("Password") <~ vMatchWidth
            <~ TYPE_PASSWORD
        ),

        w[AppCompatButton] <~ vMatchWidth <~ llLayoutMargin(top = 24 dp, bottom = 24 dp)
          <~ padding(all = 12 dp) <~ id(Id.btnSignUp) <~ tvText("Create Account"),

        w[TextView] <~ tvText("Already a member? Login") <~ CommonStyle.LinkStyle
      )
    )
  }
}