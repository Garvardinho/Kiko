package com.garvardinho.kiko.view.recyclerviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.recyclerviews.MovieListSource
import com.squareup.picasso.Picasso

class UpcomingMoviesAdapter(private val movieList: MovieListSource)
    : RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>(), MoviesAdapter {

    private var onItemClickListener: KOnItemClickListener? = null

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

    override fun setOnItemClickListener(onItemClickListener: KOnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: AppCompatImageView = itemView.findViewById(R.id.upcoming_movie_image)
        private val favorite: AppCompatImageView = itemView.findViewById(R.id.upcoming_button_favorite)
        private val title: TextView = itemView.findViewById(R.id.upcoming_movie_title)
        private val date: TextView = itemView.findViewById(R.id.upcoming_date)

        init {
            itemView.setOnClickListener { v ->
                onItemClickListener?.setListener(v, adapterPosition)
            }
        }

        fun setData(cardData: MovieResultDTO) {
            Picasso
                .get()
                .load("https://www.themoviedb.org/t/p/original/${cardData.poster_path}")
                .placeholder(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_panorama)!!)
                .into(image)

            favorite.background = if (cardData.isFavourite == true)
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart)
            else
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart_outline)

            title.text = cardData.title
            date.text = cardData.release_date
        }
    }
}
