package com.garvardinho.kiko.view.home

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.HomeFragmentBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.home.HomeViewPresenter
import com.garvardinho.kiko.screens.AndroidScreens
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class HomeFragment : MvpAppCompatFragment(), HomeView, BackButtonListener {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter { HomeViewPresenter(App.instance.router) }
    private val nowPlayingMoviesAdapter by lazy { NowPlayingMoviesAdapter(presenter.nowPlayingCardViewPresenter) }
    private val upcomingMoviesAdapter by lazy { UpcomingMoviesAdapter(presenter.upcomingCardViewPresenter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setNowPlayingMoviesData() {
        binding.nowPlayingView.layoutManager = LinearLayoutManager(requireContext())
            .apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.nowPlayingView.adapter = nowPlayingMoviesAdapter

        nowPlayingMoviesAdapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                App.instance.router.replaceScreen(AndroidScreens.detailsScreen(
                    presenter.nowPlayingCardViewPresenter.getMovie(position))
                )
            }
        })

        nowPlayingMoviesAdapter.setOnFavoriteClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                manageFavorite(presenter.nowPlayingCardViewPresenter.getMovie(position))
            }
        })
    }

    private fun setUpcomingMoviesData() {
        binding.upcomingView.layoutManager = LinearLayoutManager(requireContext())
            .apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.upcomingView.adapter = upcomingMoviesAdapter

        upcomingMoviesAdapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                App.instance.router.replaceScreen(AndroidScreens.detailsScreen(
                    presenter.upcomingCardViewPresenter.getMovie(position))
                )
            }
        })
        upcomingMoviesAdapter.setOnFavoriteClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                manageFavorite(presenter.upcomingCardViewPresenter.getMovie(position))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager: SearchManager = requireActivity()
            .getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem
            ?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            }
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showNowPlayingMovies(movies: List<MovieResultDTO>) {
        showNowPlayingLoading(false)
        setNowPlayingMoviesData()
    }

    override fun showUpcomingMovies(movies: List<MovieResultDTO>) {
        showUpcomingLoading(false)
        setUpcomingMoviesData()
    }

    override fun showNowPlayingLoading(loading: Boolean) {
        binding.nowPlayingLoadingIndicator.visibility = when (loading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        binding.nowPlayingView.visibility = when (loading) {
            true -> View.GONE
            false -> View.VISIBLE
        }
    }

    override fun showUpcomingLoading(loading: Boolean) {
        binding.upcomingLoadingIndicator.visibility = when (loading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        binding.upcomingView.visibility = when (loading) {
            true -> View.GONE
            false -> View.VISIBLE
        }
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

    override fun backPressed(): Boolean {
        return presenter.onBackPressed()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
