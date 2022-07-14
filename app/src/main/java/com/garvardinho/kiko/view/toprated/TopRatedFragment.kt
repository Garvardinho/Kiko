package com.garvardinho.kiko.view.toprated

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.FragmentTopRatedBinding
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.presenter.toprated.TopRatedViewPresenter
import com.garvardinho.kiko.view.screens.AndroidScreens
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import com.garvardinho.kiko.view.home.SORT_BY_DATE
import com.garvardinho.kiko.view.home.SORT_BY_RATING
import com.garvardinho.kiko.view.home.SORT_BY_TITLE
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
        binding.topRatedFilter.setOnClickListener { filterImage ->
            val popupMenu = PopupMenu(requireContext(), filterImage)
            popupMenu.inflate(R.menu.movie_filter)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.by_rating -> presenter.filterTopRatedMovies(SORT_BY_RATING)
                    R.id.by_date -> presenter.filterTopRatedMovies(SORT_BY_DATE)
                    R.id.by_title -> presenter.filterTopRatedMovies(SORT_BY_TITLE)
                }

                true
            }
            popupMenu.show()
        }
    }

    override fun showTopRatedMovies(movies: List<MovieDTO>) {
        binding.topRated.visibility = VISIBLE
        binding.ratingsView.visibility = VISIBLE
        binding.topRatedFilter.visibility = VISIBLE
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
        binding.topRated.visibility = GONE
        binding.topRatedFilter.visibility = GONE
        binding.ratingsView.visibility = GONE
        binding.loadingIndicator.visibility = VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun filterTopRatedMovies() {
        binding.ratingsView.adapter?.notifyDataSetChanged()
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
