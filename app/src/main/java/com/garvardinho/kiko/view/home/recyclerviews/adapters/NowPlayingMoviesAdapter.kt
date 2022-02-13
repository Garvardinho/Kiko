package com.garvardinho.kiko.view.home.recyclerviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.home.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.home.recyclerviews.MovieListSource

class NowPlayingMoviesAdapter(private val movieList: MovieListSource)
    : RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder>(), MoviesAdapter {

    private var onItemClickListener: KOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.now_playing_card_view, parent, false)
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
        private var image: AppCompatImageView = itemView.findViewById(R.id.movie_image)
        private val favorite: AppCompatImageView = itemView.findViewById(R.id.button_favorite)
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val year: TextView = itemView.findViewById(R.id.movie_year)
        private val rating: TextView = itemView.findViewById(R.id.movie_rating)

        init {
            itemView.setOnClickListener { v ->
                onItemClickListener?.setListener(v, adapterPosition)
            }
        }

        fun setData(cardData: MovieResultDTO) {
            image.setImageDrawable(AppCompatResources.getDrawable(itemView.context,
                R.drawable.ic_heart))
            favorite.background = if (cardData.isFavourite == true)
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart)
            else
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_heart_outline)

            title.text = cardData.title
            year.text = cardData.release_date?.substring(0, 4)
            rating.text = cardData.vote_average.toString()
        }
    }
}