package com.example.macroid.ui.main

import android.content.{ComponentName, Intent}
import android.widget.{ImageButton, LinearLayout, TextView}
import com.example.macroid.ui.intro.WalkThroughActivity
import com.example.macroid.ui.login.LoginActivity
import com.example.macroid.ui.register.RegisterActivity
import macroid.{Tweak, Ui, ActivityContextWrapper}
import macroid.FullDsl._

import com.example.macroid.ui.R

import com.example.macroid.ui.TextTweak._
/**
  * Created by interaction on 6/14/16.
  */
trait Layout {
  def layout(implicit context: ActivityContextWrapper) = {
    val resources = context.application.getResources
    getUi(
      l[LinearLayout](
        w[TextView] <~ text("Main Activity") <~ tvColorRes(R.color.ascent) <~ On.Click {
          Ui {

            val target = new Intent()
            //http://stackoverflow.com/questions/1135248/scala-equivalent-of-java-java-lang-classt-object
            // why you cant use LoginActivity.class
            target.setClass(context.application, classOf[LoginActivity])
            context.getOriginal.startActivity(target)
          }
        },
        w[ImageButton] <~
          Tweak[ImageButton]{view =>
            // resources.getColor api is available at sdk 23, otherwise use context.getColor instead
            view.setImageDrawable(resources.getDrawable(R.drawable.add_circle, null))
            view.setBackgroundColor(resources.getColor(R.color.white, null))
          }

        ,
        w[TextView] <~ text("Register Activity") <~ On.click {
          Ui {
            val action = new Intent(context.getOriginal, classOf[RegisterActivity])
            context.getOriginal.startActivity(action)
          }
        },

        w[TextView] <~ text("WalkThrough Activity") <~ On.click {
          Ui {
            val action = new Intent(context.getOriginal, classOf[WalkThroughActivity])
            context.getOriginal.startActivity(action)
          }
        }
      )
    )
  }
}
