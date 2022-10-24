package com.example.challenge5.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge5.R
import com.example.challenge5.adapter.MovieAdapter
import com.example.challenge5.databinding.FragmentDataBinding
import com.example.challenge5.model.GetNowPlayingResponse
import com.example.challenge5.model.GetNowPlayingResponseItem
import com.example.challenge5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataFragment : Fragment() {

    private var _binding : FragmentDataBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<GetNowPlayingResponseItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager = LinearLayoutManager(context)

        ApiClient.instance.getNowPlaying()
            .enqueue(object: Callback<GetNowPlayingResponse>{
                override fun onResponse(
                    call: Call<GetNowPlayingResponse>,
                    response: Response<GetNowPlayingResponse>
                ) {
                    val responseCode = response.code()

                    response.body()?.let { list.addAll(it.data) }
                    val adapter = MovieAdapter(list,requireContext())
                    binding.rvData.adapter = adapter
                }

                override fun onFailure(call: Call<GetNowPlayingResponse>, t: Throwable) {
                    Log.i(TAG, "Something unexpected happened to our request: " );
                }

            }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}