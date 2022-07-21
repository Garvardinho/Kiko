package com.garvardinho.kiko.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.*
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.FragmentMovieDetailsBinding
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.presenter.details.MovieDetailsViewPresenter
import com.garvardinho.kiko.setFavoriteImage
import com.garvardinho.kiko.setTextWithBoldTitle
import com.garvardinho.kiko.view.BackButtonListener
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class MovieDetailsFragment : MvpAppCompatFragment(), DetailsView, BackButtonListener {

    private var _movieDTO: MovieDTO? = null
    private val movie get() = _movieDTO!!
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter {
        MovieDetailsViewPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _movieDTO = it.getParcelable(MOVIE)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    @SuppressLint("WrongConstant")
    private fun setData() {
        binding.movieTitle.setTextWithBoldTitle(getString(R.string.details_movie_title),
            movie.title)
        binding.movieDate.setTextWithBoldTitle(getString(R.string.details_movie_date),
            movie.release_date ?: "Unknown")
        binding.movieRating.setTextWithBoldTitle(getString(R.string.details_movie_rating),
            movie.vote_average.toString())
        binding.movieOverview.setTextWithBoldTitle(getString(R.string.details_movie_overview),
            movie.overview ?: "")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.movieOverview.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
            .into(binding.movieImage)
        binding.buttonFavorite.setFavoriteImage(movie.isFavorite)

        binding.buttonFavorite.setOnClickListener {
            movie.isFavorite = !movie.isFavorite
            binding.buttonFavorite.setFavoriteImage(movie.isFavorite)
            presenter.manageFavorite(movie)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        searchItem?.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().supportFragmentManager.popBackStack()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val MOVIE = "MovieDetailsFragment.Movie"

        @JvmStatic
        fun newInstance(movieDTO: MovieDTO) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE, movieDTO)
                }
            }
    }

    override fun backPressed(): Boolean {
        return presenter.onBackPressed()
    }
}