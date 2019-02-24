package com.mobiotics.demo.mobioticsapp.ui.videolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.picasso.OkHttp3Downloader
import com.mobiotics.demo.mobioticsapp.R
import com.mobiotics.demo.mobioticsapp.models.RetroVideo

import com.squareup.picasso.Picasso


class CustomAdapter(private val context: Context,
                    private val dataList: List<RetroVideo>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    lateinit var itemOnClick: (View, RetroVideo) -> Unit

    inner class CustomViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView
        var description: TextView
        val coverImage: ImageView

        init {

            txtTitle = mView.findViewById(R.id.title)
            description = mView.findViewById(R.id.description)
            coverImage = mView.findViewById(R.id.coverImage)
            mView.setOnClickListener {
                itemOnClick.invoke(it, dataList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].title
        holder.description.text = dataList[position].description

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position].thumb!!)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}