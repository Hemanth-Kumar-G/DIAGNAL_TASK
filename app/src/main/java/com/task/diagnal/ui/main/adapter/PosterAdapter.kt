package com.task.diagnal.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.diagnal.R
import com.task.diagnal.data.PostData
import com.task.diagnal.databinding.PosterItemBinding
import java.util.*
import kotlin.collections.ArrayList


class PosterAdapter(private val posterList: ArrayList<PostData.Page.ContentItems.Content>) :
    RecyclerView.Adapter<PosterAdapter.PosterViewHolder>(), Filterable {

    companion object {
        const val DRAWABLE = "drawable"
        const val PLACE_HOLDER = "placeholder_for_missing_posters"
    }

    lateinit var context: Context
    private val mfilter: NewFilter by lazy { NewFilter() }
    var searchText: String = ""
    private val local by lazy { ArrayList<PostData.Page.ContentItems.Content>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        context = parent.context
        return PosterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.poster_item, parent, false
            )
        )
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.binding.posterData = posterList[position]

        val resources: Resources = context.resources

        var resourceId = resources.getIdentifier(
            posterList[position].posterImage.substringBefore("."), DRAWABLE, context.packageName
        )

        try {
            holder.binding.ivPosterImage.setImageDrawable(
                ResourcesCompat.getDrawable(context.resources, resourceId, null)
            )
        } catch (e: Exception) {
            resourceId = resources.getIdentifier(PLACE_HOLDER, DRAWABLE, context.packageName)
            holder.binding.ivPosterImage.setImageDrawable(
                ResourcesCompat.getDrawable(context.resources, resourceId, null)
            )
        }

        if (searchText.isNotEmpty()) {
            val index: Int =
                posterList[position].name.toLowerCase().indexOf(searchText.toLowerCase())
            val spannable = SpannableStringBuilder()
            spannable.append(posterList[position].name)
            if (index >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.YELLOW), index, index + searchText.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                holder.binding.tvPosterTitle.text = spannable
            }
        }
    }

    override fun getItemCount(): Int = posterList.size

    class PosterViewHolder(val binding: PosterItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setLocalData(productDetails: ArrayList<PostData.Page.ContentItems.Content>) {
        local.clear()
        local.addAll(productDetails)
    }

    override fun getFilter(): Filter = mfilter

    inner class NewFilter() : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            posterList.clear()
            if (charSequence.isEmpty()) {
                searchText = ""
                posterList.addAll(local)
            } else {
                val filterPattern =
                    charSequence.toString().toLowerCase(Locale.getDefault()).trim { it <= ' ' }
                searchText = filterPattern
                for (local in local) {
                    if (local.name.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        posterList.add(local)
                    }
                }
            }
            val results = FilterResults()
            results.values = posterList
            results.count = posterList.size
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            notifyDataSetChanged()
        }
    }

}