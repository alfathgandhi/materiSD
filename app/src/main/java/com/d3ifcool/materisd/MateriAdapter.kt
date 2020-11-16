package com.d3ifcool.materisd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MateriAdapter(var clickListener:MateriClick): RecyclerView.Adapter<MateriAdapter.ViewHolder>() {

    var data = listOf<Materi>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val view = layout.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(data.get(position), clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kelas: TextView = itemView.findViewById(R.id.tv_stage)


        fun initialize(item: Materi, action: MateriClick) {
            kelas.text = item.nama


            itemView.setOnClickListener {
                action.onClicked(adapterPosition, item)
            }

        }


    }
}