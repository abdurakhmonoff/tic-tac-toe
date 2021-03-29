package com.aoff.tictactoe.ui.welcome

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aoff.tictactoe.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var configurationDialog: BottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        configurationDialog = BottomSheetFragment.newInstance()
        initStartButton()
        initExitButton()
        startAnimation()

        return binding.root
    }

    private fun startAnimation() {
        AnimatorSet().apply {
            playTogether(
                animateToRight(binding.exitButton),
                animateToRight(binding.startButton),
                fadeIn(binding.startButton),
                fadeIn(binding.exitButton),
                animateToBottom(binding.welcome),
                animateToBottom(binding.appName),
                fadeIn(binding.welcome),
                fadeIn(binding.appName)
            )
        }.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = View.VISIBLE
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    private fun animateToRight(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -300f, 0f).apply {
            duration = 2000
            disableViewDuringAnimation(view)
        }
    }

    private fun animateToBottom(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -100f, 0f).apply {
            duration = 2000
            disableViewDuringAnimation(view)
        }
    }

    private fun fadeIn(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
            duration = 2000
            disableViewDuringAnimation(view)
        }
    }

    private fun initExitButton() {
        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initStartButton() {
        binding.startButton.setOnClickListener {
            configurationDialog.show(
                requireActivity().supportFragmentManager,
                "Show game configuration dialog"
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}