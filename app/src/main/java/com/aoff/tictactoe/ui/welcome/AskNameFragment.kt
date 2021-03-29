package com.aoff.tictactoe.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aoff.tictactoe.R
import com.aoff.tictactoe.databinding.FragmentAskNameBinding

class AskNameFragment : Fragment() {
    private var _binding: FragmentAskNameBinding? = null
    private val binding get() = _binding!!
    private lateinit var starterPlayer: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAskNameBinding.inflate(inflater, container, false)
        val arguments = AskNameFragmentArgs.fromBundle(requireArguments())
        starterPlayer = arguments.starterPlayer
        initNextButton()
        return binding.root
    }

    private fun initNextButton() {
        binding.nextButton.setOnClickListener {
            val firstPlayerName = binding.player1NameInput.text.toString()
            val secondPlayerName = binding.player2NameInput.text.toString()
            if (firstPlayerName.isEmpty()) {
                binding.player1InputContainer.error = getString(R.string.enter_name_error_message)
            }
            if (secondPlayerName.isEmpty()) {
                binding.player2InputContainer.error = getString(R.string.enter_name_error_message)
            }
            if (firstPlayerName.isNotEmpty() && secondPlayerName.isNotEmpty()) {
                findNavController().navigate(
                    AskNameFragmentDirections.actionAskNameFragmentToGameFragment(
                        starterPlayer,
                        "multiplayer",
                        firstPlayerName,
                        secondPlayerName
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}