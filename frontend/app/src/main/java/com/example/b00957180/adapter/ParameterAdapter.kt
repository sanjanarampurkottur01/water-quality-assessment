package com.example.b00957180.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b00957180.R
import com.example.b00957180.model.Parameter

class ParameterAdapter(private var dataSet: Array<Parameter>) :
    RecyclerView.Adapter<ParameterAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View, private val itemClickListener: OnItemClickListener?) :
        RecyclerView.ViewHolder(view) {
        val nameText: TextView
        val valueText: TextView
        val limitText: TextView

        init {
            // Initialize views and set click listener for the item
            nameText = view.findViewById(R.id.param_name)
            valueText = view.findViewById(R.id.param_value)
            limitText = view.findViewById(R.id.param_limit)

            view.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for a single city item and return a ViewHolder
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_parameter, viewGroup, false)

        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Bind data to views in ViewHolder
        val record = dataSet[position]

        viewHolder.nameText.text = record.name
        viewHolder.limitText.text = record.limit
        viewHolder.valueText.text = record.result
    }

    override fun getItemCount() = dataSet.size

    // Interface for handling item click events
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun updateData(newData: Array<Parameter>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}