package com.app.figure.UI.Activity

import android.graphics.text.LineBreaker
import android.os.Build
import com.app.figure.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.BlogCommentModel
import com.app.figure.Model.BlogModel
import com.app.figure.UI.Adapter.anotherAdapter.commentBlogAdapter
import com.app.figure.config.baseURL
import com.app.figure.viewmodel.fragmentBlogViewModel
import com.squareup.picasso.Picasso
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

class detailBlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_blog)

        var blog : BlogModel = intent.getParcelableExtra("dataBlog")!!

        setData(blog)
        showCommentBlog(blog.id_blog.toString())
        backActivity()
        hideSystemUI()

    }

    fun showCommentBlog(id : String){
        var viewModel = ViewModelProvider(this).get(fragmentBlogViewModel::class.java)

        var recyclerView : RecyclerView = findViewById(R.id.listCommentBlog)

        var adapter : commentBlogAdapter? = commentBlogAdapter(this)

        viewModel.getDataCommentBlogs().observe(this, Observer<List<BlogCommentModel>> {

            if(it!=null){
                adapter?.getList(it)
                adapter?.notifyDataSetChanged()
            }
        })
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        viewModel.ApiCallCommentBlog(id)
        recyclerView.adapter = adapter

    }

    fun setData(blogModel: BlogModel){

        var blog_bg : ImageView = findViewById(R.id.blog_bg)
        var title : TextView = findViewById(R.id.text_blog)
        var auth : TextView = findViewById(R.id.info)
        var like : TextView = findViewById(R.id.like)
        var dislike:TextView = findViewById(R.id.dislike)
        var expandable_text: HtmlTextView = findViewById(R.id.htmlTextView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            expandable_text.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }


        title.text = blogModel?.title
        auth.text = blogModel?.fullname + " - " + blogModel?.dates
        like.text = blogModel?.feellike.toString()
        dislike.text = blogModel?.feeldislike.toString()
        val img = baseURL().BASE_URL+"/layout/Img/"+blogModel?.img_bg
        Picasso.get().load(img).placeholder(R.drawable.ic_bg_2_1).into(blog_bg)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            expandable_text.setHtml(
                blogModel.content!!,
                HtmlHttpImageGetter(expandable_text, "n", true)
            );
        } else {
            expandable_text.setHtml(
                blogModel.content!!,
                HtmlHttpImageGetter(expandable_text, "n", true)
            );
        }



        Log.w("data","show data :" + Html.fromHtml(blogModel.content))

    }

    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {

            finish()
        }
    }


    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(R.id.content)
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