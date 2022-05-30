package com.app.figure.UI.Adapter.anotherAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.R

class labelProductAdapter (val context: Context, val list: List<String>) : RecyclerView.Adapter<labelProductAdapter.viewHolder>() {

    class  viewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title :TextView = view.findViewById(R.id.textLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rounder_border,parent,false)

        return labelProductAdapter.viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val title  = list[position]

        holder.title.text = title

    }

    override fun getItemCount(): Int {
        return  list.size
    }

}