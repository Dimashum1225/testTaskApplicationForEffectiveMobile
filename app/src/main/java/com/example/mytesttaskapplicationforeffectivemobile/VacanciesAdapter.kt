package com.example.mytesttaskapplicationforeffectivemobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import models.Vacancy

class Vacancy_Adapter(private val vacancies: List<Vacancy>):RecyclerView.Adapter<Vacancy_Adapter.VacancyViewHolder>() {

    private var currenList: List<Vacancy> = vacancies.take(3)
    inner class VacancyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imgV_favorite_Vacancy: ImageView = itemView.findViewById(R.id.favorite_img)
        val txtV_lookingNow:TextView = itemView.findViewById(R.id.looking_now)
        val txtV_speciality:TextView = itemView.findViewById(R.id.speciality)
        val txtV_city:TextView = itemView.findViewById(R.id.city)
        val txtV_company:TextView = itemView.findViewById(R.id.company)
        val txtV_workExperiens:TextView = itemView.findViewById(R.id.work_experience)
        val txtV_publishedDate:TextView = itemView.findViewById(R.id.publishedDate)
           }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Vacancy_Adapter.VacancyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancies,parent,false)
        return VacancyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: Vacancy_Adapter.VacancyViewHolder, position: Int) {
        val currentVacancy = currenList[position]

        holder.txtV_lookingNow.text = "Сейчас просматривают - ${currentVacancy.lookingNumber}"
        holder.txtV_speciality.text = currentVacancy.title
        holder.txtV_city.text = currentVacancy.address.town
        holder.txtV_company.text = currentVacancy.company
        holder.txtV_workExperiens.text = currentVacancy.experience.previewText
        holder.txtV_publishedDate.text = currentVacancy.publishedDate

        val isFavorite = currentVacancy.isFavorite


    }
    override fun getItemCount(): Int {
        return currenList.size
    }
    fun showAllItems() {
        currenList = vacancies // Показываем полный список
        notifyDataSetChanged() // Обновляем адаптер
    }
}
