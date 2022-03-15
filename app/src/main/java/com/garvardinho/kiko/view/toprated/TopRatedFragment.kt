package com.garvardinho.kiko.view.toprated

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.databinding.FragmentTopRatedBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.openFragment
import com.garvardinho.kiko.presenter.toprated.TopRatedViewPresenter
import com.garvardinho.kiko.view.KOnItemClickListener
import com.garvardinho.kiko.view.details.MovieDetailsFragment
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class TopRatedFragment : MvpAppCompatFragment(), TopRatedView {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter { TopRatedViewPresenter() }
    private val adapter by lazy { TopRatedMoviesAdapter(presenter.topRatedCardViewPresenter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTopRatedMovies()
    }

    override fun showTopRatedMovies(movies: List<MovieResultDTO>) {
        binding.ratingsFragmentContent.visibility = VISIBLE
        binding.loadingIndicator.visibility = GONE
        binding.ratingsView.layoutManager = LinearLayoutManager(context)
            .apply { orientation = LinearLayoutManager.VERTICAL }
        binding.ratingsView.adapter = adapter

        adapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                requireActivity().supportFragmentManager
                    .openFragment(MovieDetailsFragment
                        .newInstance(presenter.topRatedCardViewPresenter.getMovie(position))
                    )
            }
        })

        adapter.setOnFavoriteClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                manageFavorite(presenter.topRatedCardViewPresenter.getMovie(position))
            }
        })
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

    override fun manageFavorite(movie: MovieResultDTO) {
        presenter.manageFavorite(movie)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
