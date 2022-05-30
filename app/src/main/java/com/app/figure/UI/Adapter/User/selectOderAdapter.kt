package com.app.figure.UI.Adapter.User

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.getDataOrderModel
import com.app.figure.R

class selectOderAdapter(private val context: Context) : RecyclerView.Adapter<selectOderAdapter.viewHolder>()  {

    private var list : ArrayList<getDataOrderModel>? = null

    fun setList(list : ArrayList<getDataOrderModel>){
        this.list = list
        notifyDataSetChanged()
    }

    class viewHolder(view : View) : RecyclerView.ViewHolder(view){
        var stt : TextView = view.findViewById(R.id.stt)
        var magd : TextView = view.findViewById(R.id.magd)
        var date : TextView = view.findViewById(R.id.dates)
        var status : TextView = view.findViewById(R.id.status)
        var xem : TextView = view.findViewById(R.id.xem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_table,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       var order = list?.get(position)
        holder.stt.text = position.toString()
        holder.magd.text = order?.maGD.toString()
        holder.date.text = order?.dates
        if(order?.statuss == 1){
            holder.status.text = "Đã thanh toán"
        }else{
            holder.status.text = "Chưa thanh toán"
        }
    }

    override fun getItemCount(): Int {
        if(list == null){
            return  0;
        }else{
            return list?.size!!
        }

    }
}