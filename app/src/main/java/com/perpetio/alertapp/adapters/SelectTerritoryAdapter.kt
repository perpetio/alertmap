package com.perpetio.alertapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StateModel

class SelectTerritoryAdapter(
    val states: List<StateModel>
) : RecyclerView.Adapter<SelectTerritoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_select_territory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(states[position])
    }

    override fun getItemCount(): Int {
        return states.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(state: StateModel) {
            checkBox.apply {
                text = state.name
                setOnCheckedChangeListener(null)
                isChecked = state.isChecked
                setOnCheckedChangeListener { button, isChecked ->
                    state.isChecked = isChecked
                }
            }
        }
    }
}