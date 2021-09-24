package us.powerdesigninc.actionsheet

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReadableMap
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.Integer.max
import kotlin.math.roundToInt


class ActionSheetDialog internal constructor(context: Context, optionMap: ReadableMap, private val onClickCallback: Callback) : BottomSheetDialog(context) {
    private val cancelOption: ActionSheetOption

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
        val hideCancelButton = if (optionMap.hasKey("hideCancelButton")){
            optionMap.getBoolean("hideCancelButton")
        } else {
            false
        }

        cancelOption = optionList[cancelIndex] as ActionSheetOption

        when {
            hideCancelButton -> {
                // hide cancel button and no cancel in the list
                cancelButton.visibility = View.GONE
                optionList.removeAt(cancelIndex)
            }
            cancelIndex == destructiveIndex -> {
                // when cancelIndex == destructiveIndex, show cancel button in the list
                cancelButton.visibility = View.GONE
            }
            else -> {
                // show cancel button
                optionList.removeAt(cancelIndex)
            }
        }

        cancelButton.text = cancelOption.text
        cancelButton.setOnClickListener{ onCancel() }

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


        setOnCancelListener { onCancel() }
        setContentView(sheetView)

        // remove white background
        (sheetView.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.let {
            val windowRect = Rect()

            it.decorView.getWindowVisibleDisplayFrame(windowRect)

            // if it is horizontal mode, then set peek is 70% of the height and 50% of the width
            if (windowRect.width() > windowRect.height()) {
                behavior.peekHeight = (windowRect.height() * 0.7).roundToInt()
                behavior.maxWidth = (windowRect.width() * 0.5).roundToInt()
            }
        }
    }

    private fun onSelected(option: ActionSheetOption) {
        onClickCallback(option.index)
        dismiss()
    }

    private fun onCancel() {
        onClickCallback(cancelOption.index)
        dismiss()
    }
}