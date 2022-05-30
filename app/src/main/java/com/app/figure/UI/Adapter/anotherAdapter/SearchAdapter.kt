package com.app.figure.UI.Adapter.anotherAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.productModels
import com.app.figure.R
import com.app.figure.UI.Activity.detailProductActivity
import java.util.zip.Inflater

class searchAdapter(private var context: Context) : RecyclerView.Adapter<searchAdapter.viewHolder>() {

    private var list : List<productModels>? = null

    fun getList(list : List<productModels>){
        this.list = list
        notifyDataSetChanged()
    }

    class viewHolder (view : View) : RecyclerView.ViewHolder(view){
        var text: TextView = view.findViewById(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false)
        return  viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var resultSearch = this.list?.get(position)
        holder.text.text = resultSearch?.name_pro
        holder.text.setOnClickListener{
            var intent  = Intent(context, detailProductActivity::class.java)
            intent.putExtra("data",resultSearch)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      if(list != null){
          return list!!.size
      }
        return 0
    }

}