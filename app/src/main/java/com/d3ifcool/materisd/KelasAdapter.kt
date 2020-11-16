package com.d3ifcool.materisd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KelasAdapter(var clickListener:RecycleListClickListener): RecyclerView.Adapter<KelasAdapter.ViewHolder>(){

    var data= listOf<Kelas>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layout=LayoutInflater.from(parent.context)
        val view = layout.inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.initialize(data[position],clickListener)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val kelas:TextView=itemView.findViewById(R.id.tv_stage)

        fun initialize(item:Kelas,action:RecycleListClickListener){
            kelas.text=item.nama

            itemView.setOnClickListener {
                action.onClicked(adapterPosition,item)
            }

        }



    }
}