package com.app.figure.UI.Adapter.anotherAdapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.commentModels
import com.app.figure.R
import com.app.figure.config.baseURL
import com.squareup.picasso.Picasso

class commentAdapter(var context : Context) : RecyclerView.Adapter<commentAdapter.viewHolder>() {


    private var list: List<commentModels>? = null

    fun getList( list: List<commentModels>) {
        this.list = list
        notifyDataSetChanged()
    }


    class viewHolder (view : View) : RecyclerView.ViewHolder(view){
        var titleName: TextView = view.findViewById(R.id.titleName)
        var details: TextView = view.findViewById(R.id.detailsComment)
        var img_cm: ImageView = view.findViewById(R.id.img_cm)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var comment = list?.get(position)

        holder.titleName.text = comment?.fullname + " - " + comment?.date
        holder.details.text = Html.fromHtml(comment?.content)
        val img =  baseURL().BASE_URL+"/layout/Img/"+comment?.img
        Picasso.get().load(img).placeholder(R.drawable.cate1).into(holder.img_cm)

    }

    override fun getItemCount(): Int {
        if(list == null){
            return  0;
        }else{
            return list?.size!!
        }
    }

}