package com.task.diagnal.ui.main

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.task.diagnal.data.PostData
import com.task.diagnal.di.scope.ActivityScoped
import com.task.diagnal.ui.main.adapter.PosterAdapter
import com.task.diagnal.utilities.GridSpacingItemDecoration
import com.task.diagnal.utilities.Util
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainModule {

    companion object {
        const val SPAN_COUNT_THREE = "countThree"
        const val SPAN_COUNT_FIVE = "countFive"
    }

    @Named(SPAN_COUNT_FIVE)
    @Provides
    @ActivityScoped
    fun provideGridSpacingItemDecorationFive(context: MainActivity): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(5, Util.dpToPx(context, 10), true)
    }

    @Named(SPAN_COUNT_THREE)
    @Provides
    @ActivityScoped
    fun provideGridSpacingItemDecorationThree(context: MainActivity): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(3, Util.dpToPx(context, 10), true)
    }


    @Named(SPAN_COUNT_FIVE)
    @Provides
    @ActivityScoped
    fun provideGridManager(context: MainActivity): GridLayoutManager {
        return GridLayoutManager(context, 5)
    }

    @Named(SPAN_COUNT_THREE)
    @Provides
    @ActivityScoped
    fun provideGridManagerThree(context: MainActivity): GridLayoutManager {
        return GridLayoutManager(context, 3)
    }


    @Provides
    @ActivityScoped
    fun provideArrayList() = ArrayList<PostData.Page.ContentItems.Content>()

    @Provides
    @ActivityScoped
    fun provideAdapter(list: ArrayList<PostData.Page.ContentItems.Content>) = PosterAdapter(list)
}