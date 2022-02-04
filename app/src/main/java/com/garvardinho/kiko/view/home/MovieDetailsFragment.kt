package com.garvardinho.kiko.view.home

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
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
        binding.movieTitle.setTextWithBoldTitle(getString(R.string.details_movie_title),
            movie.title)
        binding.movieDate.setTextWithBoldTitle(getString(R.string.details_movie_date),
            getString(R.string.movie_date,
                movie.year,
                movie.month,
                movie.dayOfMonth))
        binding.movieRating.setTextWithBoldTitle(getString(R.string.details_movie_rating),
            movie.rating.toString())
        binding.movieDescription.setTextWithBoldTitle(getString(R.string.details_movie_description),
            movie.description ?: "")
        binding.movieImage.setImageDrawable(AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_heart))
        binding.buttonFavorite.setImageDrawable(
            if (movie.isFavourite)
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_heart)
            else
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_heart_outline))
    }

    private fun TextView.setTextWithBoldTitle(title: String, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml("<b>$title</b> $text",
                Html.FROM_HTML_MODE_COMPACT)
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
        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE, movie)
                }
            }
    }
}