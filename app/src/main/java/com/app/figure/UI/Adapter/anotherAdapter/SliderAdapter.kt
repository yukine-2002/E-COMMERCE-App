package com.app.figure.UI.Adapter.anotherAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.sliderModel
import com.app.figure.R
import com.squareup.picasso.Picasso

class SliderAdapter (private var context: Context, private var list: List<sliderModel>) : RecyclerView.Adapter<SliderAdapter.viewHolder>() {
    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var image: ImageView = view.findViewById(R.id.image)
        var linear: LinearLayout = view.findViewById(R.id.LinearCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slide_card,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       val slide = list[position]
        holder.title.text = slide.title
        holder.linear.setBackgroundResource(slide.imageBg)
        Picasso.get().load(slide.imageDraw).placeholder(R.drawable.notcolor).into(holder.image)
    }

    override fun getItemCount(): Int {
       return list.size
    }

}

