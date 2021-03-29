package com.aoff.tictactoe.ui.welcome

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aoff.tictactoe.databinding.GameConfigurationDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: GameConfigurationDialogBinding? = null
    private val binding get() = _binding!!
    private var starterPlayerSelected = false
    private var gameModeSelected = false
    private var starterPlayer = ""
    private var gameMode = ""

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameConfigurationDialogBinding.inflate(inflater, container, false)
        initSelectStarterPlayerCards()
        initSelectedModeCards()
        initStartGameButton()
        return binding.root
    }

    private fun initStartGameButton() {
        binding.startGameButton.isEnabled = false
        binding.startGameButton.setOnClickListener {
            if (gameMode == "single player") {
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToGameFragment(
                        starterPlayer,
                        gameMode
                    )
                )
                this.dismiss()
            } else {
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToAskNameFragment(
                        starterPlayer,
                        gameMode
                    )
                )
                this.dismiss()
            }
        }
    }

    private fun initSelectStarterPlayerCards() {
        binding.playerX.setOnClickListener {
            binding.playerX.isChecked = true
            binding.playerO.isChecked = false
            starterPlayerSelected = true
            starterPlayer = "x"
            makeStartButtonEnabled(starterPlayerSelected, gameModeSelected)
        }
        binding.playerO.setOnClickListener {
            binding.playerX.isChecked = false
            binding.playerO.isChecked = true
            starterPlayerSelected = true
            starterPlayer = "o"
            makeStartButtonEnabled(starterPlayerSelected, gameModeSelected)
        }
    }

    private fun initSelectedModeCards() {
        binding.modeSinglePlayer.setOnClickListener {
            binding.modeSinglePlayer.isChecked = true
            binding.modeMultiPlayer.isChecked = false
            gameModeSelected = true
            gameMode = "single player"
            makeStartButtonEnabled(starterPlayerSelected, gameModeSelected)
        }
        binding.modeMultiPlayer.setOnClickListener {
            binding.modeSinglePlayer.isChecked = false
            binding.modeMultiPlayer.isChecked = true
            gameModeSelected = true
            gameMode = "multiplayer"
            makeStartButtonEnabled(starterPlayerSelected, gameModeSelected)
        }
    }

    private fun makeStartButtonEnabled(starterPlayerSelected: Boolean, gameModeSelected: Boolean) {
        if (starterPlayerSelected && gameModeSelected) {
            binding.startGameButton.isEnabled = true
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        starterPlayerSelected = false
        gameModeSelected = false
        starterPlayer = ""
        gameMode = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}