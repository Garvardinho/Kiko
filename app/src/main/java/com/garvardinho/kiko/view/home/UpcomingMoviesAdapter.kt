package com.garvardinho.kiko.view.home

import android.annotation.SuppressLint
import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.UpcomingCardViewBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.CardViewPresenter
import com.garvardinho.kiko.view.KikoCardView
import com.garvardinho.kiko.setFavoriteImage
import com.garvardinho.kiko.view.KOnItemClickListener
import com.garvardinho.kiko.view.MoviesAdapter
import com.squareup.picasso.Picasso

class UpcomingMoviesAdapter(private val presenter: CardViewPresenter) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>(), MoviesAdapter {

    private var onItemClickListener: KOnItemClickListener? = null
    private var onFavoriteClickListener: KOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UpcomingCardViewBinding.inflate(
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

    inner class ViewHolder(private val cardView: UpcomingCardViewBinding) :
        RecyclerView.ViewHolder(cardView.root),
        KikoCardView {

        override var pos: Int = -1

        init {
            cardView.root.setOnClickListener { v ->
                onItemClickListener?.setListener(v, adapterPosition)
            }
        }

        override fun setImage(url: String) {
            val imageWidth = cardView.root.context.resources.getDimension(R.dimen.home_page_image_width).toInt()
            val imageHeight = cardView.root.context.resources.getDimension(R.dimen.home_page_image_height).toInt()

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/$url")
                .resize(imageWidth, imageHeight)
                .placeholder(AppCompatResources.getDrawable(cardView.root.context,
                    R.drawable.ic_film)!!)
                .into(cardView.upcomingMovieImage)
        }

        override fun setFavorite(movie: MovieResultDTO) {
            cardView.upcomingButtonFavorite.setFavoriteImage(movie.isFavorite)
            cardView.upcomingButtonFavorite.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                cardView.upcomingButtonFavorite.setFavoriteImage(movie.isFavorite)
                onFavoriteClickListener?.setListener(it, adapterPosition)
            }
        }

        @SuppressLint("WrongConstant")
        override fun setTitle(title: String) {
            cardView.upcomingMovieTitle.text = title
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cardView.upcomingMovieTitle.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
            }
        }

        override fun setDate(date: String) {
            cardView.upcomingDate.text = date
        }
    }
}
