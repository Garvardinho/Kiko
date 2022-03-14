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
import com.garvardinho.kiko.setFavoriteImage
import com.garvardinho.kiko.setTextWithBoldTitle
import com.garvardinho.kiko.view.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.recyclerviews.MovieListSource
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter(private val movieList: MovieListSource)
    : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>(), MoviesAdapter  {

    private var onItemClickListener: KOnItemClickListener? = null
    private var onFavoriteClickListener: KOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card_view, parent, false)
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

    override fun setOnFavoriteClickListener(onFavoriteClickListener: KOnItemClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: AppCompatImageView = itemView.findViewById(R.id.movie_image)
        private val favorite: AppCompatImageView = itemView.findViewById(R.id.button_favorite)
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val year: TextView = itemView.findViewById(R.id.movie_year)
        private val rating: TextView = itemView.findViewById(R.id.movie_rating)
        private val overview: TextView = itemView.findViewById(R.id.movie_overview)

        init {
            itemView.setOnClickListener { v ->
                onItemClickListener?.setListener(v, adapterPosition)
            }
        }

        fun setData(cardData: MovieResultDTO) {
            Picasso
                .get()
                .load("https://www.themoviedb.org/t/p/original/${cardData.poster_path}")
                .placeholder(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_film)!!)
                .into(image)

            favorite.setFavoriteImage(cardData.isFavorite)

            favorite.setOnClickListener {
                cardData.isFavorite = !cardData.isFavorite
                favorite.setFavoriteImage(cardData.isFavorite)
                onFavoriteClickListener?.setListener(it, adapterPosition)
            }

            title.setTextWithBoldTitle(
                itemView.context.getString(R.string.details_movie_title),
                cardData.title
            )

            year.setTextWithBoldTitle(
                itemView.context.getString(R.string.details_movie_date),
                cardData.release_date ?: "Unknown"
            )

            rating.setTextWithBoldTitle(
                itemView.context.getString(R.string.details_movie_rating),
                cardData.vote_average.toString()
            )

            overview.setTextWithBoldTitle(
                itemView.context.getString(R.string.details_movie_overview),
                itemView.context.getString(
                    R.string.overview_short,
                    cardData.overview?.substring(0, cardData.overview.indexOf(' ', 120))
                )
            )
        }
    }
}