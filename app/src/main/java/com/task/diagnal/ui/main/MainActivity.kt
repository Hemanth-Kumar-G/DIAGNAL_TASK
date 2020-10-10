package com.task.diagnal.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.task.diagnal.R
import com.task.diagnal.data.PostData
import com.task.diagnal.databinding.ActivityMainBinding
import com.task.diagnal.ui.main.adapter.PosterAdapter
import com.task.diagnal.utilities.GridSpacingItemDecoration
import com.task.diagnal.utilities.Util
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named


class MainActivity @Inject constructor() : DaggerAppCompatActivity() {

    var PAGE_COUNT = 1
    var PAGE_SIZE = 20

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Named(MainModule.SPAN_COUNT_FIVE)
    @Inject
    lateinit var gridSpacingItemDecorationFive: GridSpacingItemDecoration

    @Named(MainModule.SPAN_COUNT_THREE)
    @Inject
    lateinit var gridSpacingItemDecorationThree: GridSpacingItemDecoration

    @Named(MainModule.SPAN_COUNT_FIVE)
    @Inject
    lateinit var gridLayoutManagerFive: GridLayoutManager

    @Named(MainModule.SPAN_COUNT_THREE)
    @Inject
    lateinit var gridLayoutManagerThree: GridLayoutManager

    @Inject
    lateinit var posterList: ArrayList<PostData.Page.ContentItems.Content>

    @Inject
    lateinit var adapter: PosterAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.activity = this
        initRv()
        fetchData()
        initSearch()
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                adapter.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun initRv() {
        val orientation = this.resources.configuration.orientation
        binding.rv.addItemDecoration(
            if (orientation == Configuration.ORIENTATION_PORTRAIT) gridSpacingItemDecorationThree else gridSpacingItemDecorationFive
        )
        binding.rv.layoutManager =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) gridLayoutManagerThree else gridLayoutManagerFive

        binding.rv.adapter = adapter

        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if ((PAGE_SIZE - layoutManager.findLastVisibleItemPosition()) <= 5 && posterList.size == PAGE_SIZE) {
                    fetchData()
                    PAGE_SIZE += 20
                }
            }
        })
    }

    private fun fetchData() {
        val response = Util.readJSONFromAsset(
            this, Util.getFileName(PAGE_COUNT++)
        )
        val dataPojo = Gson().fromJson(response, PostData::class.java)
        viewModel.title.set(dataPojo.page.title)
        posterList.addAll(dataPojo.page.contentItems.content)
        adapter.setLocalData(posterList)
        adapter.notifyDataSetChanged()
    }
}