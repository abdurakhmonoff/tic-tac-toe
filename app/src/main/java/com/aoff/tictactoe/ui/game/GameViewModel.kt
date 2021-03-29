package com.aoff.tictactoe.ui.game

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.card.MaterialCardView

class GameViewModel : ViewModel() {

    private val _navigateToFinish = MutableLiveData<Boolean?>()
    val navigateToFinish: LiveData<Boolean?>
        get() = _navigateToFinish

    fun navigateToFinish() {
        _navigateToFinish.value = true
    }

    fun doneNavigating() {
        _navigateToFinish.value = null
    }

    fun checkForWin(buttons: Array<Array<MaterialCardView>>): Boolean {
        val fields = Array(3) { r ->
            Array(3) { c ->
                (buttons[r][c].getChildAt(0) as ImageView).tag
            }
        }

        for (i in 0..2) {
            if ((fields[i][0] == fields[i][1]) &&
                (fields[i][0] == fields[i][2]) &&
                (fields[i][0] != "")
            ) return true
        }

        for (i in 0..2) {
            if (
                (fields[0][i] == fields[1][i]) &&
                (fields[0][i] == fields[2][i]) &&
                (fields[0][i] != "")
            ) return true
        }

        if (
            (fields[0][0] == fields[1][1]) &&
            (fields[0][0] == fields[2][2]) &&
            (fields[0][0] != "")
        ) return true

        if (
            (fields[0][2] == fields[1][1]) &&
            (fields[0][2] == fields[2][0]) &&
            (fields[0][2] != "")
        ) return true

        return false
    }

}