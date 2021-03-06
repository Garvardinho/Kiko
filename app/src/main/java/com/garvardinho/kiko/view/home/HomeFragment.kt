package com.garvardinho.kiko.view.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.HomeFragmentBinding
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.presenter.home.HomeViewPresenter
import com.garvardinho.kiko.view.screens.AndroidScreens
import com.garvardinho.kiko.view.BackButtonListener
import com.garvardinho.kiko.view.KOnItemClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

const val SORT_BY_RATING: Int = 0
const val SORT_BY_DATE: Int = 1
const val SORT_BY_TITLE: Int = 2

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
        setFilters()
    }

    private fun setFilters() {
        binding.nowPlayingFilter.setOnClickListener { filterImage ->
            val popupMenu = PopupMenu(requireContext(), filterImage)
            popupMenu.inflate(R.menu.movie_filter)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.by_rating -> presenter.filterNowPlayingMovies(SORT_BY_RATING)
                    R.id.by_date -> presenter.filterNowPlayingMovies(SORT_BY_DATE)
                    R.id.by_title -> presenter.filterNowPlayingMovies(SORT_BY_TITLE)
                }

                true
            }
            popupMenu.show()
        }

        binding.upcomingFilter.setOnClickListener { filterImage ->
            val popupMenu = PopupMenu(requireContext(), filterImage)
            popupMenu.inflate(R.menu.upcoming_filter)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.by_date -> presenter.filterUpcomingMovies(SORT_BY_DATE)
                    R.id.by_title -> presenter.filterUpcomingMovies(SORT_BY_TITLE)
                }

                true
            }
            popupMenu.show()
        }
    }

    private fun setNowPlayingMoviesData() {
        binding.nowPlayingView.layoutManager = LinearLayoutManager(requireContext())
            .apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.nowPlayingView.adapter = nowPlayingMoviesAdapter

        nowPlayingMoviesAdapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                App.instance.router.navigateTo(AndroidScreens.detailsScreen(
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
                App.instance.router.navigateTo(AndroidScreens.detailsScreen(
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

    override fun showNowPlayingMovies(movies: List<MovieDTO>) {
        showNowPlayingLoading(false)
        setNowPlayingMoviesData()
    }

    override fun showUpcomingMovies(movies: List<MovieDTO>) {
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
        binding.nowPlayingFilter.visibility = when (loading) {
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
        binding.upcomingFilter.visibility = when (loading) {
            true -> View.GONE
            false -> View.VISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun filterNowPlayingMovies() {
        binding.nowPlayingView.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun filterUpcomingMovies() {
        binding.upcomingView.adapter?.notifyDataSetChanged()
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
