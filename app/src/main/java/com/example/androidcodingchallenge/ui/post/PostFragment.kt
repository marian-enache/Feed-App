package com.example.androidcodingchallenge.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.androidcodingchallenge.databinding.FragmentPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding: FragmentPostBinding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()

    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            PostScreenComposable(viewModel = viewModel)
        }

        viewModel.onViewCreated(args.post)
    }
}

