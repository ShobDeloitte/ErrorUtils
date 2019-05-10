package com.example.errorutils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class GenericAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>,
        GenericRecyclerObjectClickListener<T> {

    var listItems: List<T>

    constructor(listItems: List<T>) {
        this.listItems = listItems
    }

    constructor() {
        listItems = emptyList()
    }

    fun setItems(listItems: List<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(viewType, parent, false)
                , viewType, this
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position], position, this)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    abstract override fun onGenericItemClick(item: T, selectedPosition: Int)

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(
            view: View,
            viewType: Int,
            listener: GenericRecyclerObjectClickListener<T>
    ): RecyclerView.ViewHolder

    internal interface Binder<T> {
        fun bind(data: T, position: Int, listener: GenericRecyclerObjectClickListener<T>)
    }
}

interface GenericRecyclerObjectClickListener<T> {
    fun onGenericItemClick(item: T, selectedPosition: Int)
}