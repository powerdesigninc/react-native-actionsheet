package us.powerdesigninc.actionsheet

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReadableMap


class ActionSheetDialog internal constructor(context: Context, optionMap: ReadableMap, private val onClickCallback: Callback) : BottomSheetDialog(context) {
    init {
        val sheetView = this.layoutInflater.inflate(R.layout.sheet, null)

        val destructiveIndex = if (optionMap.hasKey("destructiveButtonIndex")) {
            optionMap.getInt("destructiveButtonIndex")
        } else {
            -1
        }

        // init options
        val optionList = mutableListOf<ActionSheetItem>()

        optionMap.getArray("options")!!.toArrayList().forEachIndexed { index, it ->
            optionList.add(ActionSheetOption(it.toString(), index, destructiveIndex == index))
        }

        // init cancel button
        val cancelButton = sheetView.findViewById<TextView>(R.id.button_cancel)
        val cancelIndex = optionMap.getInt("cancelButtonIndex")
        val showCancelButton = cancelIndex == destructiveIndex
        val cancelOption = if (showCancelButton) {
            // no cancel button
            cancelButton.visibility = View.GONE
            optionList[cancelIndex] as ActionSheetOption
        } else {
            // show cancel button
            optionList.removeAt(cancelIndex) as ActionSheetOption
        }

        cancelButton.text = cancelOption.text

        // init title
        if (optionMap.hasKey("title")) {
            optionList.add(0, ActionSheetTitle(optionMap.getString("title")!!, optionMap.getString("message")))
        }

        // init list

        val list = sheetView.findViewById<RecyclerView>(R.id.list)
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = ActionSheetAdapter(optionList, ::onSelected)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(divider)


        setOnCancelListener { onClickCallback.invoke(cancelOption.index) }
        setContentView(sheetView)

        // remove white background
        (sheetView.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    private fun onSelected(option: ActionSheetOption) {
        onClickCallback(option.index)
        hide()
    }
}