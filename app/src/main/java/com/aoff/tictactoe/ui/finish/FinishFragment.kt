package com.aoff.tictactoe.ui.finish

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aoff.tictactoe.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        val arguments = FinishFragmentArgs.fromBundle(requireArguments())
        initResult(arguments.result)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.retryButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.homeButton.setOnClickListener {
            findNavController().navigate(FinishFragmentDirections.actionFinishFragmentToWelcomeFragment())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initResult(result: String) {
        if (result == "draw") {
            binding.statusLabel.text = "DRAW"
            binding.status.visibility = View.GONE
        } else {
            binding.status.text = result
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}