package com.app.figure.UI.Adapter.anotherAdapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.cartModel
import com.app.figure.R
import com.app.figure.UI.Activity.MainActivity
import com.app.figure.UI.Activity.cartActivity
import com.app.figure.config.OnClickGetItemCart
import com.app.figure.config.baseURL
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso


class cartAdapter(
    private var context: Context,
    private var textView: TextView,
    private var i: Int,
    private var moneys: TextView
) : RecyclerView.Adapter<cartAdapter.viewHolder>() {


    private var list: ArrayList<cartModel>? = null

    lateinit var OnClickGetItemCart: OnClickGetItemCart

    fun getList(list: ArrayList<cartModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getOnClick(OnClickGetItemCart: OnClickGetItemCart) {
        this.OnClickGetItemCart = OnClickGetItemCart
    }


    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.findViewById(R.id.img_cart)
        var name: TextView = view.findViewById(R.id.name_cart)
        var price: TextView = view.findViewById(R.id.cart_money)
        var rating: RatingBar = view.findViewById(R.id.AVL_rating)
        var quantity: TextView = view.findViewById(R.id.count)
        var checkbox: CheckBox = view.findViewById(R.id.check)
        var sub: Button = view.findViewById(R.id.sub)
        var plus: Button = view.findViewById(R.id.plus)
        var count: TextView = view.findViewById(R.id.count)
        var delete: ImageView = view.findViewById(R.id.delete)
        var buttonAaction: LinearLayout = view.findViewById(R.id.buttonAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var cart = list?.get(position)
        var danhgia = cart?.danhgia?.toFloat()
        holder.name.text = cart?.name_pro
        holder.price.text = cart?.price
        holder.rating.rating = danhgia!!
        holder.quantity.text = cart?.quantity
        val img = baseURL().BASE_URL + "/layout/Img/" + cart?.img
        Picasso.get().load(img).placeholder(R.drawable.kiem_ngau).into(holder.img)

        holder.delete.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setMessage("Are you sure you want to delete this items?")
                .setPositiveButton("Delete") { dialog, which ->
                    var sharedPreference = context.getSharedPreferences("data_cart", Context.MODE_PRIVATE)!!
                    var cart = sharedPreference.getString("cart", "")
                    var gson = Gson()
                    var item = object : TypeToken<ArrayList<cartModel>>() {}.type
                    var getCart: ArrayList<cartModel> = gson.fromJson(cart, item)!!
                    getCart.removeAt(position)
                    var addcart = gson.toJson(getCart)
                    sharedPreference.edit().putString("cart", addcart).commit()
                    list?.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, list!!.size);
                    Toast.makeText(context, "p = " + position, Toast.LENGTH_LONG).show()
                     i--
                    OnClickGetItemCart.getItem(i)
                    var a : Int = list!!.size
                    if(a == 0){
                        sharedPreference?.edit()?.clear()?.apply()
                        (context as cartActivity).recreate()
                    }

                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()

        }

        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                var a = LinearLayout.LayoutParams(holder.delete.layoutParams)
                a.marginEnd = 0
                holder.delete.layoutParams = a
                holder.delete.visibility = View.VISIBLE
                holder.buttonAaction.translationX = -75F
                i++

            } else {
                var a = LinearLayout.LayoutParams(holder.delete.layoutParams)
                a.marginEnd = -1000
                holder.delete.layoutParams = a
                holder.delete.visibility = View.GONE
                holder.buttonAaction.translationX = 0F
                i--
            }
            if (i == list?.size) {
                textView.text = "Xóa tất cả"
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.GONE
            }
            OnClickGetItemCart.getItem(i)

        }

        holder.sub.setOnClickListener {
            cart?.let { it1 -> sub(holder.count, it1, position) }
        }
        holder.plus.setOnClickListener {
            cart?.let { it1 -> plus(holder.count, it1) }
        }

    }


    fun sub(count: TextView, cart: cartModel, position: Int) {
        var i = count.text.toString().toInt()
        var money: Int = moneys.text.toString().toInt()
        i--
        var sharedPreferences = context.getSharedPreferences("data_cart", Context.MODE_PRIVATE)
        var carts = sharedPreferences.getString("cart", "")!!
        var gson = Gson()
        var item = object : TypeToken<List<cartModel>>() {}.type
        var getCart: ArrayList<cartModel> = gson.fromJson<ArrayList<cartModel>>(carts, item)!!

        if (i == 0) {
            MaterialAlertDialogBuilder(context)
                .setMessage("Are you sure you want to delete this items?")
                .setPositiveButton("Delete") { dialog, which ->
                    getCart.removeAt(position)
                    money -= cart.price!!.toInt()
                    if (money != null) {
                        OnClickGetItemCart.getMoney(money)
                    }
                    var addCart = gson.toJson(getCart)
                    sharedPreferences.edit().putString("cart", addCart).commit()
                    list?.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, list!!.size);
                    Toast.makeText(context, "p = " + position, Toast.LENGTH_LONG).show()
                    this.i--

                    OnClickGetItemCart.getItem(this.i)

                    var a : Int = list!!.size
                    if(a == 0){
                        sharedPreferences?.edit()?.clear()?.apply()
                        (context as cartActivity).recreate()
                    }
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    count.text = "1"
                    dialog.dismiss()
                }
                .show()
        } else {
            for (items in getCart) {
                if (items.id_pro == cart?.id_pro) {
                    var b = items.quantity!!.toInt()
                    var count = b - 1
                    money -= items.price!!.toInt()
                    items.quantity = count.toString()
                }

            }

            var addCart = gson.toJson(getCart)
            if (money != null) {
                OnClickGetItemCart.getMoney(money)
            }
            sharedPreferences.edit().putString("cart", addCart).commit()
        }

        count.text = i.toString()
    }

    fun plus(count: TextView, cart: cartModel) {
        var i = count.text.toString().toInt()
        var money: Int = moneys.text.toString().toInt()
        i++
        var sharedPreferences = context.getSharedPreferences("data_cart", Context.MODE_PRIVATE)
        var carts = sharedPreferences.getString("cart", "")!!
        var gson = Gson()
        var item = object : TypeToken<List<cartModel>>() {}.type
        var getCart: ArrayList<cartModel> = gson.fromJson<ArrayList<cartModel>>(carts, item)!!

        for (items in getCart) {
            if (items.id_pro == cart?.id_pro) {
                var b = items.quantity!!.toInt()
                var count = b + 1
                money += items.price!!.toInt()
                items.quantity = count.toString()
            }
        }

        money?.let { OnClickGetItemCart.getMoney(it) }

        var addCart = gson.toJson(getCart)
        sharedPreferences.edit().putString("cart", addCart).commit()

        count.text = i.toString()
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        } else {
            return list!!.size
        }
    }

    }