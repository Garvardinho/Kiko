package com.garvardinho.kiko.view.ratings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.databinding.FragmentRatingsBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.TopRatedViewDelegate
import com.garvardinho.kiko.presenter.TopRatedViewPresenter
import com.garvardinho.kiko.view.TopRatedView
import com.garvardinho.kiko.view.home.MovieDetailsFragment
import com.garvardinho.kiko.view.openFragment
import com.garvardinho.kiko.view.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.recyclerviews.MovieListSourceImpl
import com.garvardinho.kiko.view.recyclerviews.adapters.RatingMoviesAdapter

class RatingsFragment : Fragment(), TopRatedView {

    private var _binding: FragmentRatingsBinding? = null
    private val binding get() = _binding!!
    private val presenter: TopRatedViewDelegate = TopRatedViewPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRatingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTopRatedMovies()
    }

    override fun showTopRatedMovies(movies: List<MovieResultDTO>) {
        binding.ratingsFragmentContent.visibility = VISIBLE
        binding.loadingIndicator.visibility = GONE
        val layoutManager = LinearLayoutManager(context)
        val data = MovieListSourceImpl(movies)
        val adapter = RatingMoviesAdapter(data)
        val recyclerView: RecyclerView = binding.ratingsView

        adapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                requireActivity().supportFragmentManager
                    .openFragment(MovieDetailsFragment.newInstance(data.getCardData(position)))
            }
        })

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun showTopRatedLoading() {
        binding.ratingsFragmentContent.visibility = GONE
        binding.loadingIndicator.visibility = VISIBLE
    }

    override fun showError() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Check your Internet connection")
            .setCancelable(true)
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
