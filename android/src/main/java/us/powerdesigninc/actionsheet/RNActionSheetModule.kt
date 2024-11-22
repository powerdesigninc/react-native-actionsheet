package us.powerdesigninc.actionsheet

import com.facebook.react.bridge.*

class RNActionSheetModule internal constructor(reactContext: ReactApplicationContext?) :
  ReactContextBaseJavaModule(reactContext!!) {

  @ReactMethod
  fun show(options: ReadableMap?, onClickCallback: Callback?) {
    currentActivity!!.runOnUiThread {
      ActionSheetDialog(currentActivity!!, options!!, onClickCallback!!).apply {
        show()
      }
    }
  }

  override fun getName(): String {
    return "RNActionsheet"
  }
}