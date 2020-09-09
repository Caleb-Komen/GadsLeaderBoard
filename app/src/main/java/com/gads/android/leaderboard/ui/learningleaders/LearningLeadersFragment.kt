package com.gads.android.leaderboard.ui.learningleaders

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gads.android.leaderboard.LearnerHttpClient
import com.gads.android.leaderboard.R
import com.gads.android.leaderboard.adapter.LearningLeaderAdapter
import com.gads.android.leaderboard.network.LeaderboardService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_learning_leaders.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

/**
 * A simple [Fragment] subclass.
 */
class LearningLeadersFragment : Fragment() {

    lateinit var service: LeaderboardService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_learning_leaders, container, false)
        val retrofit =
            LearnerHttpClient.getRetrofit()
        service = retrofit.create(LeaderboardService::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isConnected()) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val response = service.learningLeadersList().awaitResponse()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.INVISIBLE
                        val learners = response.body()!!
                        val learningLeaderAdapter =
                            LearningLeaderAdapter(
                                requireContext(),
                                learners
                            )
                        val recyclerView = view.findViewById<RecyclerView>(R.id.learning_leader_rv)
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.adapter = learningLeaderAdapter
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else{
            progressBar.visibility = View.INVISIBLE
            Snackbar.make(learning_leader_rv, "Unable to retrieve data. Check your internet connection",
                Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isConnected(): Boolean{
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
        return false
    }

}
