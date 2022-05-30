package com.app.figure.UI.Adapter.productAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.cateModel
import com.app.figure.R
import com.app.figure.config.OnTextClickListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class itemCateAdapter(private var context: Context) :
    RecyclerView.Adapter<itemCateAdapter.ViewHolder>() {
    private var id :String? = null
    private var list: List<cateModel>? = null;
    lateinit var OnTextClickListener : OnTextClickListener
    //#03B4BE
    private  var row_index: Int? = 0
    fun setCate(list: List<cateModel> ){
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnLS(OnTextClickListener : OnTextClickListener){
        this.OnTextClickListener = OnTextClickListener
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var linear : LinearLayout = itemView.findViewById(R.id.setBGcolor)
        var Img_cate : CircleImageView = itemView.findViewById(R.id.imageCate)
        var name_cate : TextView = itemView.findViewById(R.id.namecate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cate,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val categori = list?.get(position)
        holder.name_cate.text = categori?.name_cate
        holder.linear.setOnClickListener(View.OnClickListener {
            row_index = position
            OnTextClickListener.onTextClick(categori?.id_cate.toString())
            notifyDataSetChanged()
        })

        if (row_index == position) {
            holder.linear.background.setTint(Color.parseColor("#03B4BE"))
            holder.name_cate.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.linear.background.setTint(Color.parseColor("#ffffff"))
            holder.name_cate.setTextColor(Color.parseColor("#ACACAC"))
        }
        var img = listOf<Int>(R.drawable.cate1,R.drawable.cate2,R.drawable.cate3,R.drawable.cate4)
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