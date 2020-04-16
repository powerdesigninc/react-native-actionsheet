package us.powerdesigninc.actionsheet;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

public class RNActionsheetModule extends ReactContextBaseJavaModule {
    private ActionSheetDialog actionSheet;

    RNActionsheetModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void show(final ReadableMap options, final Callback onClickCallback) {
        actionSheet = new ActionSheetDialog(getCurrentActivity(), options, onClickCallback);
        actionSheet.show();
    }

    @ReactMethod
    public void hide() {
        actionSheet.hide();
    }

    @Override
    public String getName() {
        return "RNActionsheet";
    }
}
