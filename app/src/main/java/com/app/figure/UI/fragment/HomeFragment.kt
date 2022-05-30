package com.app.figure.UI.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.figure.Model.*
import com.app.figure.R
import com.app.figure.UI.Adapter.anotherAdapter.SliderAdapter
import com.app.figure.UI.Adapter.blogAdapter.blogAdapter
import com.app.figure.UI.Adapter.productAdapter.*
import com.app.figure.config.OnTextClickListener
import com.app.figure.viewmodel.fragmentHomeViewModel
import com.app.figure.viewmodel.loginViewModel
import com.google.gson.Gson


class HomeFragment : Fragment() , OnTextClickListener {

    private  var id : String? = "1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        cateRecucleView(view)
        setItemCate(view, id.toString())
        getProduct(view,id.toString())

//        topProductRecucleView(view)
//        topProductBuyRecucleView(view)
//        attentionProductRecucleView(view)
//        BlogRecucleView(view)
//        slider(view)

        return view

    }

    private fun cateRecucleView(view: View) {

        var recyclerView: RecyclerView? = view.findViewById(R.id.recyvlerViewCategories)


        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var cateAdapter: categoriesHomeAdapter? = context?.let { categoriesHomeAdapter(it) }

        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
        viewModel.getDataCates().observe(viewLifecycleOwner, Observer<List<cateModel>> {

            if(it != null){
                cateAdapter?.setCate(it)
            }

        })
        viewModel.ApiCallGetCate()
        recyclerView?.adapter = cateAdapter
    }

    private fun setItemCate(view: View,id:String) {

        var recyclerView: RecyclerView? = view.findViewById(R.id.cate_sp)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var cateAdapter: itemCateAdapter? = context?.let { itemCateAdapter(it)  }
        cateAdapter?.setOnLS(this)
        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
        viewModel.getDataCates().observe(viewLifecycleOwner, Observer<List<cateModel>> {
            if(it != null){
                cateAdapter?.setCate(it)
            }

        })
        viewModel.ApiCallGetCate()

        recyclerView?.adapter = cateAdapter
    }

    override fun onTextClick(id: String) {
        view?.let { getProduct(it,id) }
    }

    fun getProduct(view: View, id: String){
        var recyclerView: RecyclerView? = view.findViewById(R.id.pr_by_cate)

        var productAdapter = context?.let { AttentionFigureAdapter(it) }

        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)

        viewModel.getDataListByCates().observe(viewLifecycleOwner, Observer<List<productModels>> {
            if (it != null) {
                print(it)
                productAdapter?.setTopFigureAdapter(it)
                productAdapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.ApigetProductByCate(id)



        recyclerView?.adapter = productAdapter
    }




//    private fun topProductRecucleView(view: View) {
//        var recyclerView: RecyclerView? = view.findViewById(R.id.recyvlerViewTopFigure)
//
//        recyclerView?.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        var topFigureAdapters = context?.let { it -> topFigureAdapter(it) }!!
//
//
//        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
//        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer<List<productModels>> {
//            if (it != null) {
//                print(it)
//                topFigureAdapters.setTopFigureAdapter(it)
//                topFigureAdapters?.notifyDataSetChanged()
//            } else {
//                Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show()
//            }
//        })
//        viewModel.ApiCallProductTop()
//
//        recyclerView?.adapter = topFigureAdapters
//    }
//

//    private fun attentionProductRecucleView(view: View) {
//        var productAdapter = context?.let { AttentionFigureAdapter(it) }
//        var recyclerView: RecyclerView? = view.findViewById(R.id.pr_by_cate)
//
//        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        var viewModel = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
//        viewModel.getDataListAttentionProductObserver()
//            .observe(viewLifecycleOwner, Observer<List<productModels>> {
//                if (it != null) {
//                    print(it)
//                    productAdapter?.setTopFigureAdapter(it)
//                    productAdapter?.notifyDataSetChanged()
//                } else {
//                    Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show()
//                }
//            })
//        viewModel.ApiCallProductAttentions()
//
//        recyclerView?.adapter = productAdapter
//    }

//    private fun BlogRecucleView(view: View) {
//        var blogBuyAdapter: blogAdapter? =  context?.let {  blogAdapter(it) }
//        var viewModels = ViewModelProvider(this).get(fragmentHomeViewModel::class.java)
//        viewModels.getDataBlogs().observe(viewLifecycleOwner, Observer<List<BlogModel>> {
//            if(it != null){
//                blogBuyAdapter?.setDataBlog(it)
//                blogBuyAdapter?.notifyDataSetChanged()
//            }
//        })
//
//        viewModels.ApiCallDataBlog()
//        var recyclerView: RecyclerView? = view.findViewById(R.id.recyvlerViewBlog)
//
//        recyclerView?.layoutManager = GridLayoutManager(context, 2)
//
//        recyclerView?.adapter = blogBuyAdapter
//    }

//    private fun slider(view: View) {
//        val viewpage: ViewPager2 = view.findViewById(R.id.locationViewpage2)
//        val list = listOf<sliderModel>(
//            sliderModel(
//                1,
//                "Giao hàng miễn phí trong covid - 19",
//                R.drawable.drivecar,
//                R.drawable.bg_sale_2,
//                true
//            ),
//            sliderModel(2, "", R.drawable.bg_round, R.drawable.salecovid, true)
//        )
//        viewpage.adapter = context?.let { SliderAdapter(it, list) }
//
//        var mHandler: Handler = Handler(Looper.getMainLooper())
//        var runnable: Runnable = Runnable {
//            var currentSlide = viewpage.currentItem
//
//            if (currentSlide == list.size - 1) {
//                viewpage.setCurrentItem(0)
//            } else {
//                viewpage.setCurrentItem(currentSlide + 1)
//            }
//
//        }
//
//        viewpage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                mHandler.removeCallbacks(runnable)
//                mHandler.postDelayed(runnable, 5000)
//            }
//        })
//
//    }


}


