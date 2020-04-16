package us.powerdesigninc.actionsheet

import com.facebook.react.bridge.*

class RNActionsheetModule internal constructor(reactContext: ReactApplicationContext?) : ReactContextBaseJavaModule(reactContext!!) {

    @ReactMethod
    fun show(options: ReadableMap?, onClickCallback: Callback?) {
        ActionSheetDialog(currentActivity!!, options!!, onClickCallback!!).apply {
            show()
        }
    }

    override fun getName(): String {
        return "RNActionsheet"
    }
}