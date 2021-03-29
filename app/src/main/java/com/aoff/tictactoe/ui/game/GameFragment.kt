package com.aoff.tictactoe.ui.game

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aoff.tictactoe.R
import com.aoff.tictactoe.databinding.FragmentGameBinding
import com.google.android.material.card.MaterialCardView

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }
    private lateinit var gameMode: String
    private lateinit var starterPlayer: String
    private lateinit var firstPlayerName: String
    private lateinit var secondPlayerName: String
    private var status = "draw"
    private lateinit var buttons: Array<Array<MaterialCardView>>
    private var turn = ""
    private var clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }

        val arguments = GameFragmentArgs.fromBundle(requireArguments())
        gameMode = arguments.gameMode
        starterPlayer = arguments.starterPlayer
        firstPlayerName = arguments.player1Name
        secondPlayerName = arguments.player2Name

        initPlayersInfo()
        initTurnInfo()

        viewModel.navigateToFinish.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToFinishFragment(status)
                )
                clickCount = 0
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initPlayersInfo() {
        if (starterPlayer == "x") {
            if (gameMode == "single player") {
                binding.player1.text = "You: ×"
                binding.player2.text = "Player 2: o"
            } else {
                binding.player1.text = "$firstPlayerName: ×"
                binding.player2.text = "$secondPlayerName: o"
            }
        } else {
            if (gameMode == "single player") {
                binding.player1.text = "You: o"
                binding.player2.text = "Player 2: ×"
            } else {
                binding.player1.text = "$firstPlayerName: o"
                binding.player2.text = "$secondPlayerName: ×"
            }
        }
    }

    private fun initTurnInfo() {
        turn = starterPlayer
        changeTurn(turn)
    }

    private fun changeTurn(turn: String) {
        binding.turn.setImageResource(
            when (turn) {
                "x" -> R.drawable.ic_x
                else -> R.drawable.ic_o
            }
        )
    }

    private fun initButton(r: Int, c: Int): MaterialCardView {
        val button: MaterialCardView = binding.root.findViewById(
            resources.getIdentifier(
                "button_$r$c",
                "id",
                requireActivity().packageName
            )
        )

        button.setOnClickListener {
            onButtonClicked(it)
        }

        val image = button.getChildAt(0) as ImageView
        image.tag = ""

        return button
    }

    private fun onButtonClicked(view: View) {
        val card = view as MaterialCardView
        val imageView = card.getChildAt(0) as ImageView

        if (imageView.drawable != null) return
        turn = if (turn == "x") {
            imageView.setImageResource(R.drawable.ic_x)
            imageView.tag = "x"
            "o"
        } else {
            imageView.setImageResource(R.drawable.ic_o)
            imageView.tag = "o"
            "x"
        }
        changeTurn(turn)
        clickCount++
        imageView.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                imageView.context,
                R.color.o_x_icon_color
            )
        )

        if (viewModel.checkForWin(buttons)) {
            if (turn == "o" && starterPlayer == "x") {
                win(1)
            } else if (turn == "x" && starterPlayer == "o") {
                win(1)
            } else {
                win(2)
            }
        } else if (clickCount == 9) {
            win(0)
        }
    }

    private fun win(player: Int) {

        when (player) {
            0 -> status = "draw"
            1 -> {
                status = if (gameMode == "single player") {
                    "You"
                } else {
                    firstPlayerName
                }
            }
            2 -> {
                status = if (gameMode == "single player") {
                    "Player 2"
                } else {
                    secondPlayerName
                }
            }
        }

        viewModel.navigateToFinish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}