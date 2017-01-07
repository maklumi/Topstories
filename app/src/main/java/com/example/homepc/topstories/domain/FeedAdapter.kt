package com.example.homepc.topstories.domain

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.homepc.topstories.R
import com.example.homepc.topstories.model.Item
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import org.jetbrains.anko.info
import org.jetbrains.anko.*


/**
 * Created by HomePC on 7/1/2017.
 */


class FeedAdapter(context: Context, private val layoutResource: Int, private val items: List<Item>) :
        ArrayAdapter<Item>(context, layoutResource), AnkoLogger {

    private val layoutInflater: LayoutInflater

    init {
        this.layoutInflater = LayoutInflater.from(context)
    }


    override fun getCount(): Int {
        return items.size
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val viewHolder: ViewHolder
        var currentView = convertView

        if (convertView == null) {

            warn("getView: called with null convertView")
            currentView = layoutInflater.inflate(layoutResource, parent, false)

            viewHolder = ViewHolder(currentView)
            currentView.tag = viewHolder

        } else {
            info("getView: provided a convertView")
            viewHolder = convertView.tag as ViewHolder
        }

        val currentItem = items[position]

        viewHolder.textViewTitle.text = currentItem.title
        viewHolder.textViewDescription.text = currentItem.description
        viewHolder.textViewPubDate.text = currentItem.pubDate

        return currentView
    }



    private inner class ViewHolder internal constructor(v: View) {
        internal val textViewTitle: TextView = v.find(R.id.textViewTitle)
        internal val textViewDescription: TextView = v.find(R.id.textViewDescription)
        internal val textViewPubDate: TextView = v.find(R.id.textViewPubDate)

    }

}

