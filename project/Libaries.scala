import sbt._


//todo: change androidV to: 23.2.0
object Libraries {
  object android {

    def androidDep(module: String) = "com.android.support" % module % Versions.androidV

    lazy val androidRecyclerview = androidDep("recyclerview-v7")
    lazy val androidCardView = androidDep("cardview-v7")
    lazy val androidDesign = androidDep("design")
  }


  object macroid {

    def macroid(module: String = "") =
      "org.macroid" %% s"macroid${if(!module.isEmpty) s"-$module" else ""}" % Versions.macroidV

    lazy val macroidRoot = macroid()
    lazy val macroidAkkaFragments = macroid("akka")
    lazy val macroidViewable = macroid("viewable")
    lazy val macroidExtras = "com.fortysevendeg" %% "macroid-extras" % Versions.macroidExtras
  }
}
