package com.example.mytesttaskapplicationforeffectivemobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import models.Offer

class OffersAdapter(private val offerList: List<Offer>,   private val onItemClick: (String) -> Unit ): RecyclerView.Adapter<OffersAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var icon:ImageView = itemView.findViewById(R.id.imageView3)
        val title:TextView = itemView.findViewById(R.id.txtV_title)
        val buttontext: TextView = itemView.findViewById(R.id.buttonText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.offers_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = offerList[position]

        val path = when(offer.id?:""){
            "near_vacancies" ->R.drawable.near_vacancies
            "level_up_resume" ->R.drawable.level_up_resume
            "temporary_job" ->R.drawable.temporary_job
            else -> R.drawable.chech_mark
        }
        holder.itemView.setOnClickListener{
            onItemClick(offer.link)
        }
        holder.icon.setImageResource(path)
        holder.title.text = offer.title
        holder.buttontext.text = offer.buttontext ?: ""
    }

    override fun getItemCount(): Int {
        return offerList.size
    }
}

