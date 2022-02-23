package com.garvardinho.kiko.view.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.content.res.AppCompatResources
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.FragmentMovieDetailsBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.setTextWithBoldTitle
import com.squareup.picasso.Picasso

private const val MOVIE = "MovieDetailsFragment.Movie"

class MovieDetailsFragment : Fragment() {

    private var _movieDTO: MovieResultDTO? = null
    private val movie get() = _movieDTO!!
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

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

    private fun setData() {
        binding.movieTitle.setTextWithBoldTitle(getString(R.string.details_movie_title),
            movie.title)
        binding.movieDate.setTextWithBoldTitle(getString(R.string.details_movie_date), movie.release_date ?: "Unknown")
        binding.movieRating.setTextWithBoldTitle(getString(R.string.details_movie_rating),
            movie.vote_average.toString())
        binding.movieOverview.setTextWithBoldTitle(getString(R.string.details_movie_overview),
            movie.overview ?: "")
        Picasso
            .get()
            .load("https://www.themoviedb.org/t/p/original/${movie.poster_path}")
            .into(binding.movieImage)
        binding.buttonFavorite.setImageDrawable(
            if (movie.isFavourite == true)
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_heart)
            else
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_heart_outline))
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
        @JvmStatic
        fun newInstance(movieDTO: MovieResultDTO) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE, movieDTO)
                }
            }
    }
}