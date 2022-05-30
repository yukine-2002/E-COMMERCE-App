package com.app.figure.UI.Activity

import android.graphics.Paint
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.*
import com.app.figure.R
import com.app.figure.UI.Adapter.anotherAdapter.commentAdapter
import com.app.figure.UI.Adapter.productAdapter.topFigureAdapter
import com.app.figure.config.baseURL
import com.app.figure.retrofit.retroInstance
import com.app.figure.viewmodel.detailProductViewModel
import com.app.figure.viewmodel.fragmentHomeViewModel
import com.app.figure.viewmodel.fragmentProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.math.roundToInt


class detailProductActivity : AppCompatActivity() {

    private val Base_URL =  baseURL().BASE_URL+"/layout/Img/"
    private lateinit var obj: productModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        obj = intent.getParcelableExtra("data")!!

        val Img: ImageView = findViewById(R.id.img_main)

        var ImgIteam1: ImageView = findViewById(R.id.Imageitem1)
        var ImgIteam2: ImageView = findViewById(R.id.Imageitem2)
        var ImgIteam3: ImageView = findViewById(R.id.Imageitem3)
        var ImgIteam4: ImageView = findViewById(R.id.Imageitem4)

        setData(obj)
        var img = obj.image
        if (img != null) {
            itemImageAdapter(img,obj.id_pro,ImgIteam1,ImgIteam2,ImgIteam3,ImgIteam4,Img)
            Picasso.get().load(Base_URL + img)
                .placeholder(R.drawable.notcolor).into(Img)
        }
        init(obj)
    }

    private fun init(obj: productModels) {
        backActivity()
        topProductRecucleView()
        comment(obj.id_pro)
        addToCart(obj)
        putcomment(obj.id_pro)
        actionCart()
        Raiting(obj)
        hideSystemUI()
        addFavProduct(obj.id_pro.toString())
    }


    private fun addFavProduct(id : String){
        var fav : AppCompatImageView = findViewById(R.id.fav)
        fav.setOnClickListener {
            var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
            var Info: String = sharedPreferences.getString("infoUser", "")!!
            var token: String = sharedPreferences.getString("token", "")!!
            if (!token.isNullOrEmpty()) {
                var gson = Gson()
                var getInfo: person = gson.fromJson(Info,person::class.java)
                var viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
                viewModel.addFavProduct(id_user =getInfo.id_ac.toString(),id_pro = id ,this)
                retroInstance.YOUR_TOKEN = token

            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Vui lòng đăng nhập để tiếp tục")
                    .setPositiveButton("canel", { dialog, which ->
                        dialog.dismiss()
                    }).show()
            }
        }
    }


    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

    private fun setData(obj: productModels) {
        var name_pro: TextView = findViewById(R.id.details_name)
        var cate_figure: TextView = findViewById(R.id.cate_figure)
        var price_old: TextView = findViewById(R.id.price_old)
        var price_new: TextView = findViewById(R.id.price_new)
        var AVL_rating: RatingBar = findViewById(R.id.AVL_rating)
        var height_figure: TextView = findViewById(R.id.height_figure)
        var xuatsu: TextView = findViewById(R.id.xuatsu)
        var ex: ExpandableTextView = findViewById(R.id.expand_text_view)
        var expandable_text: TextView = findViewById(R.id.expandable_text)
        var numRaiting: TextView = findViewById(R.id.numRaiting)

        var viewModel = ViewModelProvider(this).get(detailProductViewModel::class.java)

        viewModel.getCate().observe(this, Observer<cateModel> {
            if (it != null) {
                cate_figure.text = it.name_cate
            } else {
                cate_figure.text = "figure update"
            }
        })

        viewModel.ApiCate(obj.id_Cate.toString())
        val df = DecimalFormat("#.#")

        name_pro.text = obj.name_pro
        price_old.text = obj.price_old + "VND"
        price_old.setPaintFlags(price_old.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        price_new.text = obj.price_new + "VND"
        AVL_rating.rating = obj.danhgia
        height_figure.text = obj.chieucao
        xuatsu.text = obj.xuatsu
        numRaiting.text=df.format(obj.danhgia)
        ex.text = Html.fromHtml(obj.mieuta)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            expandable_text.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

    }


    private fun itemImageAdapter(img4: String,id:Int,ImgIteam1 : ImageView,ImgIteam2 : ImageView,ImgIteam3 : ImageView,ImgIteam4 : ImageView,ImageChange:ImageView) {
        Picasso.get().load(Base_URL + img4)
            .placeholder(R.drawable.notcolor).into(ImgIteam4)

        var imgs1: String? = null;
        var imgs2: String? = null;
        var imgs3: String? = null;
        var imgs4: String? = Base_URL + img4

        var viewModel = ViewModelProvider(this).get(detailProductViewModel::class.java)
        viewModel.getImages().observe(this, Observer<List<imgProduct>> {

            if (!it.isEmpty()) {
                imgs1 = Base_URL+ it[0].img1
                imgs2 = Base_URL + it[0].img2
                imgs3 = Base_URL + it[0].img3


                if (!it[0].img1.isEmpty()) {
                    Picasso.get().load(imgs1)
                        .placeholder(R.drawable.notcolor).into(ImgIteam1)
                }
                if (!it[0].img2.isEmpty()) {
                    Picasso.get().load(imgs2)
                        .placeholder(R.drawable.notcolor).into(ImgIteam2)
                }
                if (!it[0].img3.isEmpty()) {
                    Picasso.get().load(imgs3)
                        .placeholder(R.drawable.notcolor).into(ImgIteam3)
                }

            }else{
                Toast.makeText(this,"data null",Toast.LENGTH_SHORT).show()
            }

        })
        viewModel.ApigetImage(id.toString())


        ImgIteam1.setOnClickListener {
            Picasso.get().load(imgs1)
                .placeholder(R.drawable.notcolor).into(ImageChange)
        }
        ImgIteam2.setOnClickListener {
            Picasso.get().load(imgs2)
                .placeholder(R.drawable.notcolor).into(ImageChange)
        }
        ImgIteam3.setOnClickListener {
            Picasso.get().load(imgs3)
                .placeholder(R.drawable.notcolor).into(ImageChange)
        }
        ImgIteam4.setOnClickListener {
            Picasso.get().load(imgs4)
                .placeholder(R.drawable.notcolor).into(ImageChange)
        }

    }


    private fun comment(id: Int) {
        var recyclerView: RecyclerView? = findViewById(R.id.listComment)
        var adapter: commentAdapter? = commentAdapter(this)
        var viewmodel = ViewModelProvider(this).get(detailProductViewModel::class.java)
        viewmodel.getComments().observe(this, Observer<List<commentModels>> {
            if (it != null) {
                adapter?.getList(it)
                adapter?.notifyDataSetChanged()
            }
        })
        viewmodel.ApiComment(id.toString())

        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter

    }

    private fun actionCart() {
        var sub: Button = findViewById(R.id.sub)
        var plus: Button = findViewById(R.id.plus)
        var count: TextView = findViewById(R.id.count)
        var i = count.text.toString().toInt()
        sub.setOnClickListener {
            --i
            if (i == 0) {
                i = 1
            }
            count.text = i.toString()
        }
        plus.setOnClickListener {
            ++i
            count.text = i.toString()
        }

    }


    private fun topProductRecucleView() {

        var recyclerView: RecyclerView? = findViewById(R.id.figure_2)

        recyclerView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var topFigureAdapters: topFigureAdapter = topFigureAdapter(this)

        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer<List<productModels>> {
            if (it == null) {
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show()
            } else {
                print(it)
                topFigureAdapters.setTopFigureAdapter(it)
                topFigureAdapters?.notifyDataSetChanged()
            }
        })
        viewModel.ApiCallProductTop()
        recyclerView?.adapter = topFigureAdapters
    }

    private fun addToCart(obj: productModels){
        var btnAdd : Button = findViewById(R.id.addCart)

        btnAdd.setOnClickListener {
            var quantity : TextView = findViewById(R.id.count)
            var gson  = Gson()
            val preferences = getSharedPreferences("data_cart", MODE_PRIVATE)

            var check = preferences?.getString("cart","")?.isEmpty()
            if(check == true){
                var cartModel : cartModel? = null
                var list = ArrayList<cartModel>()
                if(obj.price_old!!.isEmpty()){
                    cartModel = cartModel(obj.id_pro,obj.image,obj.name_pro,obj.price_new,obj.danhgia.toString(),quantity.text.toString())
                }else{
                    cartModel  = cartModel(obj.id_pro,obj.image,obj.name_pro,obj.price_old,obj.danhgia.toString(),quantity.text.toString())
                }
                list?.add(cartModel)
                var addCart = gson.toJson(list)
                preferences.edit().putString("cart",addCart).commit()
                Log.e("test1","v1")
            }else{
                var cart = preferences?.getString("cart","")
                val itemType = object : TypeToken<List<cartModel>>() {}.type

                var getCart : ArrayList<cartModel> = gson.fromJson<ArrayList<cartModel>>(cart,itemType)!!

                var check  = getCart.filter{ it -> it.id_pro == obj.id_pro}

                if(check.isEmpty()){
                    var cartModel : cartModel? = null
                    if(obj.price_old!!.isEmpty()){
                        cartModel = cartModel(obj.id_pro,obj.image,obj.name_pro,obj.price_new,obj.danhgia.toString(),quantity.text.toString())
                    }else{
                        cartModel  = cartModel(obj.id_pro,obj.image,obj.name_pro,obj.price_old,obj.danhgia.toString(),quantity.text.toString())
                    }
                    getCart?.add(cartModel)
                    var addCart = gson.toJson(getCart)
                    preferences.edit().putString("cart",addCart).commit()
                }else{
                    for (item in getCart){
                        if(item.id_pro == obj.id_pro){
                           var a = quantity.text.toString().toInt()
                           var b =  item.quantity!!.toInt()
                            var count = a + b
                            item.quantity = count.toString()
                        }
                    }
                    var addCart = gson.toJson(getCart)
                    preferences.edit().putString("cart",addCart).commit()
                }
                if (cart != null) {
                    var carts = preferences?.getString("cart","")
                    Log.e("check",carts.toString())
                }

            }

        }

    }

    private fun putcomment(id : Int){
        var button : Button = findViewById(R.id.buttonComment)
        var conent : TextView = findViewById(R.id.comment)
        button.setOnClickListener {


            var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
            var Info: String = sharedPreferences.getString("infoUser", "")!!
            var token: String = sharedPreferences.getString("token", "")!!
            if (!token.isNullOrEmpty()) {
                var gson = Gson()
                var getInfo: person = gson.fromJson(Info, person::class.java)
                var viewModel = ViewModelProvider(this).get(detailProductViewModel::class.java)
                viewModel.PutComment(id, conent.text.toString(), getInfo.id_ac.toString())
                retroInstance.YOUR_TOKEN = token
                comment(id)

            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Vui lòng đăng nhập để tiếp tục")
                    .setPositiveButton("canel", { dialog, which ->
                        dialog.dismiss()
                    }).show()
            }
        }
    }

    private fun Raiting(obj: productModels){
        var AVL_rating: RatingBar = findViewById(R.id.AVL_rating)
        var numRaiting: TextView = findViewById(R.id.numRaiting)
        val df = DecimalFormat("#.#")
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var Info: String = sharedPreferences.getString("infoUser", "")!!
        var token: String = sharedPreferences.getString("token", "")!!
        var viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
        AVL_rating.setOnRatingBarChangeListener(object: RatingBar.OnRatingBarChangeListener{

            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

                if (!token.isNullOrEmpty()) {

                    var gson = Gson()
                    var getInfo: person = gson.fromJson(Info,person::class.java)
                    retroInstance.YOUR_TOKEN = token
                    viewModel.getDataRaitings().observe(this@detailProductActivity, Observer {
                        if(it != null){
                            if(!df.format(it.toFloat()).equals("40.1")){
                                Log.e("rai : " , it)
                                ratingBar?.setIsIndicator(false)
                                AVL_rating.rating = it.toFloat()
                                numRaiting.text = df.format(it.toFloat())
                            }else {
                                ratingBar?.setIsIndicator(true)
                                ratingBar?.rating = obj.danhgia
                                numRaiting.text = df.format(obj.danhgia)
                                Toast.makeText(this@detailProductActivity,"Vui lòng mua sản phẩm để được đánh giá",Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                    viewModel.RateProduct(id_user = getInfo.id_ac.toString(),index = ratingBar?.rating.toString(),id_pro = obj.id_pro.toString() )

                } else {
                    ratingBar?.setIsIndicator(true)
                    ratingBar?.rating = obj.danhgia
                    numRaiting?.text = df.format(obj.danhgia)
                    Toast.makeText(this@detailProductActivity,"Vui lòng đăng nhập để được đánh giá",Toast.LENGTH_LONG).show()
                }

            }
        })

    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

}

