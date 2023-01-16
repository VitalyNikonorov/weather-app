package net.nikonorov.weather.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.nikonorov.weather.R
import net.nikonorov.weather.presentation.location.LocationItem

/**
 * List of locations' adapter
 */
class LocationsListAdapter(private val onItemClick: (LocationItem) -> Unit) :
    RecyclerView.Adapter<LocationsListAdapter.ViewHolder>() {

    private val data: MutableList<LocationItem> = mutableListOf()

    // FIXME optimise data change
    fun setData(newData: List<LocationItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.location_item_view, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemLabel.text = data[position].itemLabel
        holder.itemData = data[position]
        val backgroundColor =
            if (data[position].isSelected) R.color.purple_selected else R.color.transparent
        holder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context, backgroundColor
            )
        )
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View, onItemClick: (LocationItem) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val itemLabel: TextView = itemView.findViewById(R.id.item_label)
        var itemData: LocationItem? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemData?.let(onItemClick)
                }
            }
        }
    }
}
