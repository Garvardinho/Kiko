package com.garvardinho.kiko.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.model.Movie

class UpcomingMoviesAdapter(private val movieList: MovieListSource)
    : RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(movieList.getCardData(position))
    }

    override fun getItemCount(): Int {
        return movieList.getSize()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: AppCompatImageView = itemView.findViewById(R.id.upcoming_movie_image)
        private val favorite: AppCompatImageView = itemView.findViewById(R.id.upcoming_button_favorite)
        private val title: TextView = itemView.findViewById(R.id.upcoming_movie_title)
        private val date: TextView = itemView.findViewById(R.id.upcoming_date)

        fun setData(cardData: Movie) {
            image.setImageDrawable(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart_outline))
            favorite.background = if (cardData.isFavourite)
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart)
            else
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart_outline)

            title.text = cardData.title
            date.text = itemView.context.getString(
                R.string.upcoming_date,
                cardData.year,
                cardData.month,
                cardData.dayOfMonth)
        }
    }
}
