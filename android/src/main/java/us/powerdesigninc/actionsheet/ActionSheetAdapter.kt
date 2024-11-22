package us.powerdesigninc.actionsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

const val ActionSheetTypeTitle = 0;
const val ActionSheetTypeOption = 1;

interface ActionSheetItem;

data class ActionSheetTitle(val title: String, val message: String?) : ActionSheetItem;

data class ActionSheetOption(val text: String, val index: Int, val destructive: Boolean) :
  ActionSheetItem;

class ActionSheetOptionViewHolder(view: View) : RecyclerView.ViewHolder(view)

class ActionSheetAdapter(
  private val options: List<ActionSheetItem>,
  private val onSelected: (ActionSheetOption) -> Unit
) : RecyclerView.Adapter<ActionSheetOptionViewHolder>() {

  override fun getItemViewType(position: Int): Int {
    return if (options[position] is ActionSheetTitle) {
      ActionSheetTypeTitle
    } else {
      ActionSheetTypeOption
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ActionSheetOptionViewHolder {
    val view = if (viewType == ActionSheetTypeTitle) {
      LayoutInflater.from(parent.context)
        .inflate(R.layout.title, parent, false)
    } else {
      LayoutInflater.from(parent.context)
        .inflate(R.layout.item, parent, false)
    }

    return ActionSheetOptionViewHolder(view)
  }

  override fun onBindViewHolder(holder: ActionSheetOptionViewHolder, position: Int) {
    val item = options[position];
    if (item is ActionSheetTitle) {
      holder.itemView.findViewById<TextView>(R.id.title).text = item.title

      if (!item.message.isNullOrEmpty()) {
        val messageView = holder.itemView.findViewById<TextView>(R.id.message)
        messageView.text = item.message
        messageView.visibility = View.VISIBLE
      }
    } else if (item is ActionSheetOption) {
      holder.itemView.tag = item;

      holder.itemView.setOnClickListener {
        onSelected(it.tag as ActionSheetOption)
      }

      val textView = holder.itemView.findViewById<TextView>(R.id.text)
      textView.text = item.text

      if (item.destructive) {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.destructive))
      } else {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.option))
      }
    }
  }

  override fun getItemCount(): Int {
    return options.size
  }

}