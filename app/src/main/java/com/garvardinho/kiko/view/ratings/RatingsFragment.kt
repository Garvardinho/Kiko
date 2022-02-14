package com.garvardinho.kiko.view.ratings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.databinding.FragmentRatingsBinding
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.RatingMoviesService
import com.garvardinho.kiko.view.home.MovieDetailsFragment
import com.garvardinho.kiko.view.openFragment
import com.garvardinho.kiko.view.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.recyclerviews.MovieListSourceImpl
import com.garvardinho.kiko.view.recyclerviews.adapters.RatingMoviesAdapter

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_MOVIES_EXTRA = "MOVIES"

class RatingsFragment : Fragment() {

    private var _binding: FragmentRatingsBinding? = null
    private val binding get() = _binding!!
    private val loadResultsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            binding.ratingsFragmentContent.visibility = VISIBLE
            binding.loadingIndicator.visibility = GONE
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_DATA_EMPTY_EXTRA -> Log.e("ERROR", DETAILS_DATA_EMPTY_EXTRA)
                DETAILS_RESPONSE_EMPTY_EXTRA -> Log.e("ERROR", DETAILS_RESPONSE_EMPTY_EXTRA)
                DETAILS_REQUEST_ERROR_EXTRA -> Log.e("ERROR", DETAILS_REQUEST_ERROR_EXTRA)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> Log.e("ERROR",
                    DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                DETAILS_URL_MALFORMED_EXTRA -> Log.e("ERROR", DETAILS_URL_MALFORMED_EXTRA)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    setMoviesData(intent.getParcelableArrayListExtra(
                        DETAILS_MOVIES_EXTRA)!!) // !! used because data emptiness is processed above
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRatingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                loadResultsReceiver,
                IntentFilter(DETAILS_INTENT_FILTER)
            )

            it.startService(Intent(it, RatingMoviesService::class.java))
        }
        binding.ratingsFragmentContent.visibility = GONE
        binding.loadingIndicator.visibility = VISIBLE
    }

    private fun setMoviesData(movies: ArrayList<MovieResultDTO>) {
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

    override fun onDestroyView() {
        _binding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroyView()
    }
}