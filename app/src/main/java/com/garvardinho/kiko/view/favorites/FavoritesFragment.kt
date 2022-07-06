package com.garvardinho.kiko.view.favorites

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.databinding.FragmentFavoritesBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.favorites.FavoritesViewPresenter
import com.garvardinho.kiko.screens.AndroidScreens
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FavoritesFragment : MvpAppCompatFragment(), FavoritesView, BackButtonListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter { FavoritesViewPresenter(App.instance.router) }
    private val adapter by lazy { FavoriteMoviesAdapter(presenter.favoritesCardViewPresenter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadingIndicator.visibility = View.VISIBLE
        binding.favoritesView.visibility = View.GONE
        binding.emptyData.visibility = View.GONE
    }

    override fun showFavoriteMovies(movies: List<MovieResultDTO>) {
        binding.loadingIndicator.visibility = View.GONE
        binding.favoritesView.visibility = View.VISIBLE
        if (movies.isEmpty()) {
            binding.emptyData.visibility = View.VISIBLE
            return
        }

        binding.favoritesView.layoutManager = LinearLayoutManager(context)
            .apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.favoritesView.adapter = adapter

        adapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                App.instance.router.navigateTo(AndroidScreens.detailsScreen(
                    presenter.favoritesCardViewPresenter.getMovie(position))
                )
            }
        })

        adapter.setOnFavoriteClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                manageFavorite(presenter.favoritesCardViewPresenter.getMovie(position))
            }
        })
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