package com.garvardinho.kiko.view.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.TopRatedCardViewBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.CardViewPresenter
import com.garvardinho.kiko.view.KikoCardView
import com.garvardinho.kiko.setFavoriteImage
import com.garvardinho.kiko.setTextWithBoldTitle
import com.garvardinho.kiko.view.KOnItemClickListener
import com.garvardinho.kiko.view.MoviesAdapter
import com.squareup.picasso.Picasso

class TopRatedMoviesAdapter(private val presenter: CardViewPresenter) :
    RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>(), MoviesAdapter {

    private var onItemClickListener: KOnItemClickListener? = null
    private var onFavoriteClickListener: KOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TopRatedCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

    override fun setOnItemClickListener(onItemClickListener: KOnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun setOnFavoriteClickListener(onFavoriteClickListener: KOnItemClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    inner class ViewHolder(private val cardView: TopRatedCardViewBinding) :
        RecyclerView.ViewHolder(cardView.root),
        KikoCardView {

        override var pos: Int = -1

        init {
            cardView.root.setOnClickListener { v ->
                onItemClickListener?.setListener(v, adapterPosition)
            }
        }

        override fun setImage(url: String) {
            val imageWidth = cardView.root.context.resources.getDimension(R.dimen.top_rated_image_width).toInt()
            val imageHeight = cardView.root.context.resources.getDimension(R.dimen.top_rated_image_height).toInt()

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/$url")
                .resize(imageWidth, imageHeight)
                .placeholder(AppCompatResources.getDrawable(cardView.root.context,
                    R.drawable.ic_film)!!)
                .into(cardView.movieImage)
        }

        override fun setFavorite(movie: MovieResultDTO) {
            cardView.buttonFavorite.setFavoriteImage(movie.isFavorite)
            cardView.buttonFavorite.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                cardView.buttonFavorite.setFavoriteImage(movie.isFavorite)
                onFavoriteClickListener?.setListener(it, adapterPosition)
            }
        }

        override fun setTitle(title: String) {
            cardView.movieTitle.setTextWithBoldTitle(
                cardView.root.context.getString(R.string.details_movie_title),
                title
            )
        }

        override fun setDate(date: String) {
            cardView.movieDate.setTextWithBoldTitle(
                cardView.root.context.getString(R.string.details_movie_date),
                date
            )
        }

        override fun setRating(rating: Double) {
            cardView.movieRating.setTextWithBoldTitle(
                cardView.root.context.getString(R.string.details_movie_rating),
                rating.toString()
            )
        }

        override fun setOverview(overview: String) {
            val truncatedIndex = when (overview.length > 90) {true -> 90 false -> overview.length}
            val truncated = when (truncatedIndex > overview.length) {
                true -> overview.substring(0, overview.indexOf(' ', truncatedIndex))
                false -> overview
            }
            cardView.movieOverview.setTextWithBoldTitle(
                cardView.root.context.getString(R.string.details_movie_overview),
                cardView.root.context.getString(
                    R.string.overview_short,
                    truncated
                )
            )
        }
    }
}