package com.example.wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // the randomly selected word for the round
    private lateinit var wordToGuess: String

    // how many times have we already guessed?
    private var guessCount = 0

    // the views in the UI
    private lateinit var input: EditText
    private lateinit var guessButton: Button
    private lateinit var correctWordText: TextView
    private lateinit var guessTextArray: Array<TextView>
    private lateinit var guessCheckArray: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Populate variables with Views
        input = findViewById(R.id.wordEntry)
        guessButton = findViewById(R.id.guessButton)
        correctWordText = findViewById(R.id.correctWord)
        guessTextArray = arrayOf(
            findViewById(R.id.guess1),
            findViewById(R.id.guess2),
            findViewById(R.id.guess3)
        )
        guessCheckArray = arrayOf(
            findViewById(R.id.guess1check),
            findViewById(R.id.guess2check),
            findViewById(R.id.guess3check)
        )

        // Set up correct answer
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        correctWordText.visibility = View.GONE // Hide the correct answer!
        correctWordText.text = wordToGuess

        // Set up the Button to wait for guesses
        guessButton.setOnClickListener { make_guess() }
    }

    /**
     * Simple implementation
     *
     * LIMITED entry validation/cleaning
     * -- assumes all entries are valid 4-letter words
     * -- makes all characters in entries uppercase
     */
    private fun make_guess() {
        // Get guess from EditText (then clear entry field)
        var guess_input = input.text.toString().uppercase()
        input.text.clear()
        input.clearFocus()

        // Add guess to board
        guessTextArray[guessCount].text = guess_input
        guessCheckArray[guessCount].text = checkGuess(guess_input)
        guessCount++

        // Check if the game ended
        if (guess_input.equals(wordToGuess) or (guessCount == 3)) {
            correctWordText.visibility = View.VISIBLE
            guessButton.text = ""
            guessButton.setOnClickListener{ no_more_guesses() }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

    private fun no_more_guesses() {
        Toast.makeText(this, "The game is over!", Toast.LENGTH_LONG).show()
    }

}
