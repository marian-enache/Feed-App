package com.example.androidcodingchallenge.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            FeedScreenComposable(viewModel = viewModel,
                onPostClicked = { navigateToPostDetails(it) },
                onPostMarked = { post, marked -> viewModel.onPostMarkedAsFavorite(post, marked) })
        }

        viewModel.onViewCreated()
    }

    private fun navigateToPostDetails(post: PostModel) {
        val action = FeedFragmentDirections.postsToPostDetails(post)
        activity?.findNavController(R.id.nav_fragment_main)?.navigate(action)
    }
}

