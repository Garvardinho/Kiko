package com.garvardinho.kiko.view.favorites

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.databinding.FragmentFavoritesBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.openFragment
import com.garvardinho.kiko.presenter.FavoritesViewDelegate
import com.garvardinho.kiko.presenter.FavoritesViewPresenter
import com.garvardinho.kiko.view.FavoritesView
import com.garvardinho.kiko.view.home.MovieDetailsFragment
import com.garvardinho.kiko.view.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.recyclerviews.MovieListSourceImpl
import com.garvardinho.kiko.view.recyclerviews.adapters.FavoriteMoviesAdapter

class FavoritesFragment : Fragment(), FavoritesView {

    private val presenter: FavoritesViewDelegate = FavoritesViewPresenter(this)
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

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
        presenter.loadFavoriteMovies()
    }

    override fun showFavoriteMovies(movies: List<MovieResultDTO>) {
        binding.loadingIndicator.visibility = View.GONE
        binding.favoritesView.visibility = View.VISIBLE
        if (movies.isEmpty()) {
            binding.emptyData.visibility = View.VISIBLE
            return
        }
        val layoutManager = LinearLayoutManager(context)
        val data = MovieListSourceImpl(movies)
        val adapter = FavoriteMoviesAdapter(data)
        val recyclerView: RecyclerView = binding.favoritesView

        adapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                requireActivity().supportFragmentManager
                    .openFragment(MovieDetailsFragment.newInstance(data.getCardData(position)))
            }
        })

        adapter.setOnFavoriteClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                manageFavorite(data.getCardData(position))
            }
        })

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
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