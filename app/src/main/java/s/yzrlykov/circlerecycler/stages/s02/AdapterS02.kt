package s.yzrlykov.circlerecycler.stages.s02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.R

class AdapterS02(private val model: List<String>) : RecyclerView.Adapter<AdapterS02.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_title)

        fun bind(title: String, position: Int) {
            tvTitle.text = title

            val colorId =
                if (position % 2 == 0) R.color.background_even_item else R.color.background_odd_item

            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    colorId
                )
            )

        }
    }
}