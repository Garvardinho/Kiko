package com.garvardinho.kiko.view.favorites

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.FragmentFavoritesBinding
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.presenter.favorites.FavoritesViewPresenter
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import com.garvardinho.kiko.view.home.HomeFragment.Companion.SORT_BY_DATE
import com.garvardinho.kiko.view.home.HomeFragment.Companion.SORT_BY_RATING
import com.garvardinho.kiko.view.home.HomeFragment.Companion.SORT_BY_TITLE
import com.garvardinho.kiko.view.screens.AndroidScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class FavoritesFragment : MvpAppCompatFragment(), FavoritesView, BackButtonListener {

    @Inject
    lateinit var router: Router

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter {
        FavoritesViewPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }
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
        binding.favoritesFilter.setOnClickListener { filterImage ->
            val popupMenu = PopupMenu(requireContext(), filterImage)
            popupMenu.inflate(R.menu.movie_filter)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.by_rating -> presenter.filterFavoriteMovies(SORT_BY_RATING)
                    R.id.by_date -> presenter.filterFavoriteMovies(SORT_BY_DATE)
                    R.id.by_title -> presenter.filterFavoriteMovies(SORT_BY_TITLE)
                }

                true
            }
            popupMenu.show()
        }
        App.instance.appComponent.inject(this)
    }

    override fun showFavoriteMovies(movies: List<MovieDTO>) {
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
                router.navigateTo(AndroidScreens.detailsScreen(
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

    @SuppressLint("NotifyDataSetChanged")
    override fun filterFavoriteMovies() {
        binding.favoritesView.adapter?.notifyDataSetChanged()
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

    override fun manageFavorite(movie: MovieDTO) {
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