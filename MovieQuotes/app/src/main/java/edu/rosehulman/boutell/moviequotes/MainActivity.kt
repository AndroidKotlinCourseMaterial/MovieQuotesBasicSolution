package edu.rosehulman.boutell.moviequotes

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_SETTINGS
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_add_edit_quote.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
//            updateQuote(MovieQuote("I am your father", "The Empire Strikes Back"))
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a quote")
        val view = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_edit_quote, null, false)
        builder.setView(view)
        builder.setIcon(android.R.drawable.ic_input_add)
        builder.setPositiveButton(android.R.string.ok) {
            _, _ ->
                val quote = view.dialog_edit_text_quote.text.toString()
                val movie = view.dialog_edit_text_movie.text.toString()
                updateQuote(MovieQuote(quote, movie))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    fun updateQuote(movieQuote: MovieQuote) {
        quote_text_view.text = movieQuote.quote
        movie_text_view.text = movieQuote.movie
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_increase_font_size -> {
                changeFontSize(4)
                true
            }
            R.id.action_decrease_font_size -> {
                changeFontSize(-4)
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(ACTION_SETTINGS))
                true
            }
            // TODO: Create a menu item that when pressed, launches a dialog
            // to confirm the deletion. If they press OK, the quote will return to
            // the default "Movie", "Quote"
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeFontSize(delta: Int) {
        // Increase the font size by delta sp
        var currentSize = quote_text_view.textSize / resources.displayMetrics.scaledDensity
        currentSize += delta
        quote_text_view.textSize = currentSize
        movie_text_view.textSize = currentSize
    }
}
