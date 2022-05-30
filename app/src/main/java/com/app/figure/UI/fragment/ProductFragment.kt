package com.app.figure.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.productModels
import com.app.figure.R
import com.app.figure.UI.Adapter.productAdapter.productAdapter
import com.app.figure.UI.Adapter.productAdapter.topFigureAdapter
import com.app.figure.viewmodel.fragmentProductViewModel


class ProductFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        ProductAttention(view)
       topProductBuyRecucleView(view)
        getAllProduct(view)
        return view
    }

    private fun ProductAttention(view: View) {

        var recyclerView: RecyclerView? = view.findViewById(R.id.recyvlerViewProduct)

        recyclerView?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        var productAdapter = context?.let { topFigureAdapter(it) }
        var viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
        viewModel.getDataListAttentionProductObserver()
            .observe(viewLifecycleOwner, Observer<List<productModels>> {
                if (it != null) {
                    print(it)
                    productAdapter?.setTopFigureAdapter(it)
                    productAdapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show()
                }
            })
        viewModel.ApiCallProductAttentions()

        recyclerView?.adapter = productAdapter
    }


    private fun topProductBuyRecucleView(view: View) {

        var recyclerView: RecyclerView? = view.findViewById(R.id.Productbuys)

        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var topBuyFigure = context?.let { it -> topFigureAdapter(it) }

        val viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
        viewModel.getDataTopBuyObserver()
            .observe(viewLifecycleOwner, Observer<List<productModels>> {
                if (it != null) {
                    print(it)
                    topBuyFigure?.setTopFigureAdapter(it)
                    topBuyFigure?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show()
                }
            })


            viewModel.ApiCallProductBuys()


        recyclerView?.adapter = topBuyFigure
    }


    private fun getAllProduct(view: View){
        var recyclerView:RecyclerView = view.findViewById(R.id.recycleAllpr)
        var adapter : productAdapter = context?.let { productAdapter(it) }!!
        recyclerView.layoutManager = GridLayoutManager(context,2)
        var viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
        viewModel.getProducts().observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.setTopFigureAdapter(it)
                adapter.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter
        val spanCount = 3 // 3 columns

        val spacing = 50 // 50px

        val includeEdge = true
        viewModel.ApigetAllProduct()
    }

//    private fun productLabel(view: View) {
//        var list = listOf<String>("Tất cả", "Thể loại", "Mua nhiều nhất", "Hot nhất")
//
//        val recyclerView: RecyclerView? = view.findViewById(R.id.recyvlerViewSort)
//
//        recyclerView?.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        var Adapter: labelProductAdapter? = context?.let { labelProductAdapter(it, list) }
//
//        recyclerView?.adapter = Adapter
//
//
//    }

}