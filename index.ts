import { ActionSheetIOS, NativeModules, Platform } from "react-native";

const { RNActionsheet } = NativeModules;

export interface ActionSheetOptions {
  title?: string;
  options: string[];
  cancelButtonIndex?: number;
  destructiveButtonIndex?: number;
  message?: string;
  anchor?: number;
  tintColor?: string;
  hideCancelButton?: boolean; // if you don't want to show cancel button on Android
}

export type ActionSheetCallback = (buttonIndex: number) => void;

/**
 * The across platform version (android, ios) of ActionSheet
 */
export const ActionSheet = {
  showActionSheetWithOptions(
    options: ActionSheetOptions,
    callback: ActionSheetCallback
  ) {
    if (options == null){
      throw new Error("options is required")
    }

    if (options.options == null || options.options.length == 0){
      throw new Error("options is required and not empty")
    }

    if (callback == null){
      throw new Error("callback is required")
    }

    options.cancelButtonIndex = options.cancelButtonIndex ?? 0

    if (Platform.OS === "ios") {
      ActionSheetIOS.showActionSheetWithOptions(options, callback);
    } else {      
      RNActionsheet.show(options, callback);
    }
  },
};

export default ActionSheet;
