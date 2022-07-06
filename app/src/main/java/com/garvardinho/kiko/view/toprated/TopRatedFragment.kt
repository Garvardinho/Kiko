package com.garvardinho.kiko.view.toprated

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.databinding.FragmentTopRatedBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.toprated.TopRatedViewPresenter
import com.garvardinho.kiko.screens.AndroidScreens
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class TopRatedFragment : MvpAppCompatFragment(), TopRatedView, BackButtonListener {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter { TopRatedViewPresenter(App.instance.router) }
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
                App.instance.router.navigateTo(AndroidScreens.detailsScreen(
                    presenter.topRatedCardViewPresenter.getMovie(position))
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

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(error)
            .setCancelable(true)
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    override fun manageFavorite(movie: MovieResultDTO) {
        presenter.manageFavorite(movie)
    }

    override fun backPressed(): Boolean {
        return presenter.onBackPressed()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
