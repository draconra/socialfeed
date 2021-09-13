package com.aditya.socialfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aditya.socialfeed.application.SocialFeedApplication
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.databinding.FragmentFeedBinding
import com.aditya.socialfeed.ui.adapter.CardFeedAdapter
import com.aditya.socialfeed.ui.dialog.AddUrlDialogFragment
import com.aditya.socialfeed.util.PlayerViewAdapter.Companion.playIndexThenPausePreviousPlayer
import com.aditya.socialfeed.util.PlayerViewAdapter.Companion.releaseAllPlayers
import com.aditya.socialfeed.util.RecyclerViewScrollListener
import com.aditya.socialfeed.viewmodel.CardFeedViewModelFactory
import com.aditya.socialfeed.viewmodel.FeedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FeedFragment : Fragment(), AddUrlDialogFragment.OnClickedListener {

    private var feedAdapter: CardFeedAdapter? = null
    private val feedList = ArrayList<CardFeed>()

    // for handle scroll and get first visible item index
    private lateinit var scrollListener: RecyclerViewScrollListener

    private val feedViewModel: FeedViewModel by viewModels {
        CardFeedViewModelFactory((activity?.application as SocialFeedApplication).repository)
    }

    private var _binding: FragmentFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()

        binding.btnPost.setOnClickListener {
            AddUrlDialogFragment.newInstance(this)
                .show(parentFragmentManager, AddUrlDialogFragment.TAG)
        }
    }

    private fun setUpView() {

        val allTasks = feedViewModel.allFeeds
        feedAdapter = CardFeedAdapter(activity, allTasks)
        binding.rvFeeds.setHasFixedSize(true)
        binding.rvFeeds.adapter = feedAdapter

        feedViewModel.allFeeds.observe(requireActivity()) { feeds ->
            feeds?.let { feedAdapter?.submitList(it) }
            if (feeds.isNotEmpty()) {
                binding.llNothing.visibility = View.GONE
            } else {
                binding.llNothing.visibility = View.VISIBLE
            }
        }

        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                // play just visible item
                if (index != -1)
                    playIndexThenPausePreviousPlayer(index)
            }

        }
        binding.rvFeeds.addOnScrollListener(scrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBtnStartClicked() {

    }

    override fun onPause() {
        super.onPause()
        releaseAllPlayers()
    }
}