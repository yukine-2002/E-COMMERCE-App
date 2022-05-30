package com.app.figure.UI.Adapter.blogAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.BlogModel
import com.app.figure.R
import com.app.figure.UI.Activity.detailBlogActivity
import com.app.figure.config.baseURL
import com.squareup.picasso.Picasso

class blogAdapter(private var context: Context) : RecyclerView.Adapter<blogAdapter.viewHolder>() {

    private var list: List<BlogModel>? = null

    fun setDataBlog(list: List<BlogModel>){
        this.list = list
        notifyDataSetChanged()
    }

    class viewHolder(view : View) : RecyclerView.ViewHolder(view){
        var textBlog : TextView = view.findViewById(R.id.text_blog)
        var img : ImageView = view.findViewById(R.id.Img_blog)
        var relativeLayout : RelativeLayout = view.findViewById(R.id.relative)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_blog,parent,false)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val blog = list?.get(position)
        holder.textBlog.text = blog?.title
        val img =  baseURL().BASE_URL+"/layout/Img/"+blog?.img_bg
        Picasso.get().load(img).placeholder(R.drawable.ic_bg_2_1).into(holder.img)
        holder.relativeLayout.setOnClickListener {
            var intent = Intent(context, detailBlogActivity::class.java)
            intent.putExtra("dataBlog",blog)
            holder.itemView.context.startActivity(intent)

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