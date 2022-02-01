package com.garvardinho.kiko.view.home

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.content.res.AppCompatResources
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.FragmentMovieDetailsBinding
import com.garvardinho.kiko.model.Movie

private const val MOVIE = "MovieDetailsFragment.Movie"

class MovieDetailsFragment : Fragment() {

    private var _movie: Movie? = null
    private val movie get() = _movie!!
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _movie = it.getParcelable(MOVIE)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.movieTitle.text = Html.fromHtml(
                "<b>${getString(R.string.details_movie_title)}</b> ${movie.title}",
                Html.FROM_HTML_MODE_COMPACT)
            binding.movieDate.text =
                Html.fromHtml("<b>${getString(R.string.details_movie_date)}</b> ${
                    getString(R.string.movie_date,
                        movie.year,
                        movie.month,
                        movie.dayOfMonth)}",
                    Html.FROM_HTML_MODE_COMPACT)
            binding.movieRating.text =
                Html.fromHtml("<b>${getString(R.string.details_movie_rating)}</b> ${
                    movie.rating}",
                    Html.FROM_HTML_MODE_COMPACT)
            binding.movieDescription.text =
                Html.fromHtml("<b>${getString(R.string.details_movie_description)}</b> ${
                    movie.description ?: ""}",
                    Html.FROM_HTML_MODE_COMPACT)
        }
        binding.movieImage.setImageDrawable(AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_heart))
        binding.buttonFavorite.setImageDrawable(
            if (movie.isFavourite)
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
        fun newInstance(movie: Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE, movie)
                }
            }
    }
}