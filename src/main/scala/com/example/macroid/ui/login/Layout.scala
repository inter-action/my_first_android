package com.example.macroid.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.macroid.ui.register.RegisterActivity

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
/**
  * Created by interaction on 6/14/16.
  *
  * this layout is identical at res/layout/acitivity_login
  */
trait Layout extends IdGeneration { self: AppCompatActivity =>
  /*
  Ids:
    Id.textEmailAddress
   */
  val tag = "LoginLayout"

  var emailIpt = slot[EditText]
  var passwordIpt = slot[EditText]
  var loginBtn = slot[AppCompatButton]

  val RESIGTER_REQUEST_CODE = Id.REGISTER_CODE

  def layout(implicit context: ActivityContextWrapper) = {

    getUi {
      CommonStyle.CommonView (
        CommonStyle.Logo,

        // Email Input
        l[TextInputLayout](
          w[EditText] <~ Tweak[EditText]{ view =>
            view.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            view.setHint("Email")
            view.setId(Id.textEmailAddress)

          } <~ vMatchWidth <~ wire(emailIpt)
        ) <~ vMatchWidth <~ llLayoutMargin(0, 8 dp, 0, 8 dp),



        l[TextInputLayout](
          w[EditText] <~ Tweak[EditText]{ view =>
            view.setHint("Password")
            view.setId(Id.textPassword)
          } <~ vMatchWidth <~ TYPE_PASSWORD <~ wire(passwordIpt)
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
        w[AppCompatButton] <~ vMatchWidth <~ padding(all = 12 dp)
          <~ llLayoutMargin(top = 24 dp, bottom = 24 dp)
          <~ id(Id.btnLogin) <~ tvText("Login") <~ wire(loginBtn)
          <~ On.Click {
          Ui {
            login()
          }
        }
        ,


        /*
        <TextView android:id="@+id/link_signup"
          android:layout_width="fill_parent"
          android:layout_height="wrap_content"
          android:layout_marginBottom="24dp"
          android:text="No account yet? Create one"
          android:gravity="center"
          android:textSize="16dip"/>
         */

        w[TextView] <~ CommonStyle.LinkStyle
          <~ tvText("No account yet? Create one") <~ On.click {
          Ui{
            val action = new Intent(context.application, classOf[RegisterActivity])
            context.getOriginal.startActivityForResult(action, RESIGTER_REQUEST_CODE)
          }
        }
      )
    }

  }

  def login()(implicit context: ActivityContextWrapper): Unit = {
    validate() match {
      case Some((email: String, password: String)) =>
        Log.d(tag, s"input valid: ${email}, ${password}")

        loginBtn.map(_.setEnabled(false))

        val progressDialog = new ProgressDialog(context.getOriginal, R.style.AppTheme_Dark_Dialog)
        progressDialog.setIndeterminate(true)
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(new Runnable {
          override def run(): Unit = {

            // On complete call either onLoginSuccess or onLoginFailed
            onLoginSuccess();
            // onLoginFailed();
            progressDialog.dismiss();
          }
        }, 3000)

      case _ =>
        Log.d(tag, "input invalid")
    }
  }

  def onLoginSuccess() = {
    loginBtn.map(_.setEnabled(true))
    finish();
  }

  def onLoginFailed() = {
    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    loginBtn.map(_.setEnabled(true))
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = {
    if (requestCode == RESIGTER_REQUEST_CODE){
        if (resultCode == Constants.RESULT_OK){
          // TODO: Implement successful signup logic here
          // By default we just finish the Activity and log them in automatically
          this.finish()
        }
    }else {
      self.onActivityResult(requestCode, resultCode, data)
    }

  }

  // much cleaner !
  def validate()(implicit context: ActivityContextWrapper) = {

    def validate(target: Option[String], input: Option[EditText], msg: String) = target match {
      case Some(value) =>
        input.map(_.setError(null))
        Some(value)
      case _ =>
        input.map(_.setError(msg))
        None
    }

    val email = emailIpt.map(_.getText.toString).filter{ value =>
      !value.isEmpty && android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
    val password = passwordIpt.map(_.getText.toString).filter{ value =>
      !value.isEmpty && value.length >= 4 && value.length <= 10
    }


    for {
      emailTxt <- validate(email, emailIpt, context.getOriginal.getString(R.string.validate_error_email))
      passwordTxt <- validate(password, passwordIpt, context.getOriginal.getString(R.string.validate_error_password))
    } yield (emailTxt, passwordTxt)

    // the code is ugly, try to refactor it
//    val ipts = for {
//      email <- emailIpt
//      password <- passwordIpt
//    } yield (email.getText.toString, password.getText.toString)
//
//    ipts match {
//      case rs: Some[(String, String)] =>
//        val (email, password) = rs.get
//        if (email.isEmpty || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//          emailIpt.map(_.setError("enter a valid email address"))
//          return false
//        }else{
//          emailIpt.map(_.setError(null))
//        }
//
//        if (password.isEmpty || password.length < 4 || password.length > 10){
//          passwordIpt.map(_.setError(""))
//          return false
//        } else {
//          passwordIpt.map(_.setError(null))
//        }
//
//        true
//
//      case None =>
//        Log.w(tag, "failed to bind email & password")
//        false
//    }
  }
}

object CommonStyle {
  def CommonView(childs: Ui[View]*)(implicit context: ActivityContextWrapper) = {

    l[ScrollView](
      l[LinearLayout](
        childs:_*
      ) <~ vMatchWidth <~ padding(24 dp, 56 dp, 24 dp, -1) <~ llVertical
    ) <~ vMatchParent <~ Tweak[ScrollView](_.setFitsSystemWindows(true))
  }

  def Logo(implicit context: ActivityContextWrapper) =
    l[LinearLayout](
      // bm: dont know why but it has to be wrapped in a Layout for margin to work
      w[ImageView] <~ Tweak[ImageView]{imageView =>
        val params = new LayoutParams(LayoutParams.WRAP_CONTENT, 72 dp)
        imageView.setLayoutParams(params)
        imageView.setBackground(ContextCompat.getDrawable(context.getOriginal, R.drawable.logo))
      } <~ gravity(Gravity.CENTER)
    ) <~ vMatchWidth <~ llLayoutMargin(top = 24 dp, bottom = 24 dp)

  def LinkStyle(implicit context: ActivityContextWrapper) =
    vWrapContent + llLayoutMargin(bottom = 24 dp) + tvSizePiexls(16 dp) + tvCenter

}

object Constants {
  val RESULT_OK = 0
}