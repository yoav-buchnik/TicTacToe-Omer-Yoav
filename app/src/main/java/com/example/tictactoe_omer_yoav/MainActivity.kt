package com.example.tictactoe_omer_yoav

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private lateinit var turnText: TextView
    private lateinit var restartButton: Button

    private var currentPlayer = "X"
    private var gameBoard = mutableListOf<String>().apply { repeat(9) { add("") } }
    private var isGameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupButtonListeners()
    }

    private fun initializeViews() {
        turnText = findViewById(R.id.turnText)
        restartButton = findViewById(R.id.playAgainButton)

        buttons = listOf(
            findViewById(R.id.button0), findViewById(R.id.button1), findViewById(R.id.button2),
            findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5),
            findViewById(R.id.button6), findViewById(R.id.button7), findViewById(R.id.button8)
        )
    }

    private fun setupButtonListeners() {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { handleCellClick(index) }
        }

        restartButton.setOnClickListener { resetGame() }
    }

    private fun handleCellClick(index: Int) {
        if (gameBoard[index].isEmpty() && isGameActive) {
            gameBoard[index] = currentPlayer
            buttons[index].text = currentPlayer

            when {
                isWinner() -> {
                    turnText.text = "Player $currentPlayer Wins!"
                    endGame()
                }
                isDraw() -> {
                    turnText.text = "It's a Draw!"
                    endGame()
                }
                else -> {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    turnText.text = "Player $currentPlayer's Turn"
                }
            }
        }
    }

    private fun isWinner(): Boolean {
        val winPatterns = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
            arrayOf(0, 4, 8), arrayOf(2, 4, 6)
        )

        return winPatterns.any { pattern ->
            gameBoard[pattern[0]] == currentPlayer &&
                    gameBoard[pattern[1]] == currentPlayer &&
                    gameBoard[pattern[2]] == currentPlayer
        }
    }

    private fun isDraw(): Boolean = gameBoard.none { it.isEmpty() }

    private fun endGame() {
        isGameActive = false
        restartButton.visibility = View.VISIBLE
    }

    private fun resetGame() {
        gameBoard = mutableListOf<String>().apply { repeat(9) { add("") } }
        currentPlayer = "X"
        isGameActive = true
        turnText.text = "Player X's Turn"
        restartButton.visibility = View.GONE

        buttons.forEach { it.text = "" }
    }
}
