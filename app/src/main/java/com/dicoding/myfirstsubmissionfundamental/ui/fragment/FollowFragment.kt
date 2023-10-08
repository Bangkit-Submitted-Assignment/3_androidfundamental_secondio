package com.dicoding.myfirstsubmissionfundamental.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfirstsubmissionfundamental.databinding.FragmentFollowBinding
import com.dicoding.myfirstsubmissionfundamental.ui.adapter.FollowAdapter
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.FollowViewModel


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null
    private val FollowViewModel by viewModels<FollowViewModel>()

    companion object{
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }


        val followersAdapter = FollowAdapter()
        val followingAdapter = FollowAdapter()

        if (position == 1){
            binding.rvFollowers.apply {
                adapter = followersAdapter
                binding.rvFollowers.adapter = adapter
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
            }

            showLoading()
            FollowViewModel.resultFollowers(username.toString())

            FollowViewModel.followersList.observe(viewLifecycleOwner) { followers ->
                followersAdapter.submitList(followers)
                hideLoading()
            }
        } else {
            binding.rvFollowers.apply {
                adapter = followingAdapter
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
            }

            showLoading()
            FollowViewModel.resultFollowing(username.toString())

            FollowViewModel.followingList.observe(viewLifecycleOwner) { following ->
                followingAdapter.submitList(following)
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

}