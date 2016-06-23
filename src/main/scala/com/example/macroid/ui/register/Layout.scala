package com.example.macroid.ui.register


import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity

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
import com.example.macroid.ui.login.Constants

trait Layout extends IdGeneration{self: AppCompatActivity =>



  var nameIpt = slot[EditText]
  var emailIpt = slot[EditText]
  var passwordIpt = slot[EditText]
  var signUpBtn = slot[AppCompatButton]
  var linkText = slot[TextView]

  val tag = "Register_Layout"
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
            <~ tvInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS) <~ wire(nameIpt)
        ),


        textInput(
          w[EditText] <~ id(Id.emailIpt) <~ hint("Email") <~ vMatchWidth
            <~ TYPE_EMAIL <~ wire(emailIpt)
        ),


        textInput(
          w[EditText] <~ id(Id.passwordIpt) <~ hint("Password") <~ vMatchWidth
            <~ TYPE_PASSWORD <~ wire(passwordIpt)
        ),

        w[AppCompatButton] <~ vMatchWidth <~ llLayoutMargin(top = 24 dp, bottom = 24 dp)
          <~ padding(all = 12 dp) <~ id(Id.btnSignUp) <~ tvText("Create Account")
          <~ wire(signUpBtn)
          <~ On.click {
            Ui {
              signUp()
            }
          },

        w[TextView] <~ tvText("Already a member? Login") <~ CommonStyle.LinkStyle <~ wire(linkText)
          <~ On.click {
            Ui {
              this.finish()
            }
          }
      )
    )
  }

  def signUp()(implicit context: ActivityContextWrapper): Unit ={
    validate() match {
      case Some((name: String, email: String, password: String)) =>
        Log.d(tag, s"input valid: ${name}, ${email}, ${password}")

        signUpBtn.map(_.setEnabled(false))

        val progressDialog = new ProgressDialog(context.getOriginal, R.style.AppTheme_Dark_Dialog)
        progressDialog.setIndeterminate(true)
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(new Runnable {
          override def run(): Unit = {

            // On complete call either onLoginSuccess or onLoginFailed
            onSuccess()
            // onLoginFailed();
            progressDialog.dismiss()
          }
        }, 3000)
      case _ =>
        Log.d(tag, "input invalid")

    }
  }

  def onSuccess() = {
    signUpBtn.map(_.setEnabled(true))
    this.setResult(Constants.RESULT_OK, null);//notify LoginActivity for result
    this.finish()
  }

  def onFailed() = {
    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show()

    signUpBtn.map(_.setEnabled(true))
  }

  def validate()(implicit context: ActivityContextWrapper) = {
    def validate(target: Option[String], input: Option[EditText], msg: String) = target match {
      case Some(value) =>
        input.map(_.setError(null))
        Some(value)
      case _ =>
        input.map(_.setError(msg))
        None
    }

    val name = nameIpt.map(_.getText.toString).filter{value =>
      !value.isEmpty && value.length >= 3
    }

    val email = emailIpt.map(_.getText.toString).filter{ value =>
      !value.isEmpty && android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
    val password = passwordIpt.map(_.getText.toString).filter{ value =>
      !value.isEmpty && value.length >= 4 && value.length <= 10
    }


    for {
      nameTxt <- validate(name, nameIpt, "at least 3 characters")
      emailTxt <- validate(email, emailIpt, context.getOriginal.getString(R.string.validate_error_email))
      passwordTxt <- validate(password, passwordIpt, context.getOriginal.getString(R.string.validate_error_password))
    } yield (nameTxt, emailTxt, passwordTxt)
  }
}