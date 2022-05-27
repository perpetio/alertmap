package com.perpetio.alertapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StateModel

class SelectStateAdapter(
    private val states: List<StateModel>,
    private val checkChangeListener: CheckChangeListener
) : RecyclerView.Adapter<SelectStateAdapter.ViewHolder>() {

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
        private val chkText: CheckedTextView = itemView.findViewById(R.id.chk_text)

        fun bind(state: StateModel) {
            chkText.apply {
                text = state.name
                isChecked = state.isChecked
                setOnClickListener {
                    chkText.isChecked = !chkText.isChecked
                    state.isChecked = chkText.isChecked
                    checkChangeListener.onCheckChange(
                        state.id,
                        chkText.isChecked
                    )
                }
            }
        }
    }

    interface CheckChangeListener {
        fun onCheckChange(stateId: Int, isChecked: Boolean)
    }
}