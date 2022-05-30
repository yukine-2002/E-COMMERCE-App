package com.app.figure.UI.Adapter.productAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.productModels
import com.app.figure.R
import com.app.figure.UI.Activity.detailProductActivity
import com.app.figure.config.baseURL
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class topFigureAdapter(private var context: Context) : RecyclerView.Adapter<topFigureAdapter.viewHolder>() {

    private var list: List<productModels>? = null;

    fun setTopFigureAdapter(list: List<productModels>){
        this.list = list
        notifyDataSetChanged()
    }
    class viewHolder(view : View) : RecyclerView.ViewHolder(view){
        var name_sp : TextView = view.findViewById(R.id.name_sp)
        var price : TextView = view.findViewById(R.id.price_sp)
        var raiting : TextView = view.findViewById(R.id.AVL_rating)
        var img_sp : ImageView = view.findViewById(R.id.Img_sp)
        val heart : AppCompatImageView = view.findViewById(R.id.heart)
        val relativeLayout : RelativeLayout = view.findViewById(R.id.figure_div)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
     val view = LayoutInflater.from(context).inflate(R.layout.viewholder_topfigure,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val product = this.list?.get(position)
        holder.name_sp.text = product?.name_pro
        holder.price.text = formatMoney(product?.price_new!!.toInt())
        holder.raiting.text = product.danhgia.toString()
        val img =  baseURL().BASE_URL+"/layout/Img/"+product?.image
        Picasso.get().load(img).placeholder(R.drawable.ic_bg_2_1).into(holder.img_sp)
        holder.heart.setColorFilter(context.getColor(R.color.Red))

        holder.relativeLayout.setOnClickListener{
            var intent  = Intent(context, detailProductActivity::class.java)
            intent.putExtra("data",product)
            holder.itemView.context.startActivity(intent)
        }

    }

    fun formatMoney(money : Int) : String {

        val localeVN = Locale("vi", "VN")
        val numberFormat = NumberFormat.getCurrencyInstance(localeVN)
        numberFormat.setMaximumFractionDigits(0);
        val convert = numberFormat.format(money)

        return convert

    }

    override fun getItemCount(): Int {
        if(list == null){
            return  0;
        }else{
            return list?.size!!
        }

    }
}