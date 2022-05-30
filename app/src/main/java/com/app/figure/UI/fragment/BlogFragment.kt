package com.app.figure.UI.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.BlogModel
import com.app.figure.R
import com.app.figure.UI.Adapter.blogAdapter.dataBlogAdapter
import com.app.figure.viewmodel.fragmentBlogViewModel


class BlogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_blog, container, false)

        showAllBlog(view)

        return view
    }

    fun showAllBlog(view: View){

        var recyclerView : RecyclerView = view.findViewById(R.id.BlogList)

        var blogAdapter : dataBlogAdapter? = context?.let { dataBlogAdapter(it) }

        var viewModel = ViewModelProvider(this).get(fragmentBlogViewModel::class.java)
        viewModel.getDataBlogs().observe(viewLifecycleOwner, Observer<List<BlogModel>> {

            if(it!=null){
                blogAdapter?.setDataBlog(it)
                blogAdapter?.notifyDataSetChanged()
            }

        })
        viewModel.ApiCallDataBlog()
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = blogAdapter

    }

}