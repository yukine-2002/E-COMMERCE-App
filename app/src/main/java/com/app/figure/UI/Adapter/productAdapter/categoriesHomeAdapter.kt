package com.app.figure.UI.Adapter.productAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.cateModel
import com.app.figure.Model.categoriesModel
import com.app.figure.Model.productModels
import com.app.figure.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class categoriesHomeAdapter(private var context: Context) :
    RecyclerView.Adapter<categoriesHomeAdapter.ViewHolder>() {

    private var list: List<cateModel>? = null;

    fun setCate(list: List<cateModel>){
        this.list = list
        notifyDataSetChanged()
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var Img_cate : ImageView = itemView.findViewById(R.id.Img_cate)
         var name_cate : TextView = itemView.findViewById(R.id.name_cate)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_categories,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categori = list?.get(position)
        var img = listOf<Int>(R.drawable.cate1,R.drawable.cate2,R.drawable.cate3,R.drawable.cate4)
        holder.name_cate.text = categori?.name_cate
        Picasso.get().load(img[position]).placeholder(R.drawable.cate1).into(holder.Img_cate)

    }

    override fun getItemCount(): Int {
        if(list == null){
            return  0;
        }else{
            return list?.size!!
        }
    }

}