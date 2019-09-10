package com.manmohan.nasaapod.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manmohan.nasaapod.R
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.databinding.ApodItemBinding
import com.squareup.picasso.Picasso


class ApodListAdapter constructor(private val context: Context, private val apodList: MutableList<Apod>) :
    RecyclerView.Adapter<ApodListAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater

    private var callBack: CallBack?=null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    fun addNewsList(newsList: List<Apod>) {
        this.apodList.clear()
        this.apodList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun setCallback(callBack: CallBack){
        this.callBack = callBack
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(ApodItemBinding.inflate(layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        val resultsEntity = apodList[pos]
        viewHolder.bind(resultsEntity)
        viewHolder.binding.card.setOnClickListener {v->
            resultsEntity.isRead = true
            notifyItemChanged(pos)
            callBack?.onClickApod(resultsEntity)
        }
    }

    override fun getItemCount(): Int {
        return apodList.size
    }

    inner class ViewHolder(val binding: ApodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(apod: Apod) {
            binding.apod = apod
            binding.executePendingBindings()
        }
    }

    companion object {

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun ImageView.setImageUrl(imageUrl: String) {
            Picasso.with(this.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(this)
        }

        @BindingAdapter("visibility")
        @JvmStatic
        fun View.setVisibility(boolean: Boolean?) {
            if (boolean!!)
                this.setVisibility(View.VISIBLE)
            else
                this.setVisibility(View.INVISIBLE)
        }
    }

    interface CallBack{
        fun onClickApod(apod: Apod)
    }
}

