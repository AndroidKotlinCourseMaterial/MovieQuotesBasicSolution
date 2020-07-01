package edu.rosehulman.boutell.moviequotes

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
            //            Log.d(Constants.TAG, "Floating action button pressed")
//            updateQuote(MovieQuote("I am your father", "The Empire Strikes Back"))
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a quote")

//        builder.setMessage("Your quote is -hey-")
//        builder.setItems(arrayOf("First", "Second", "Third")) { _, k ->
//            val quote = when (k) {
//                0 -> "First quote"
//                1 -> "Second quote"
//                else -> "Third quote"
//            }
//            updateQuote(MovieQuote(quote, "Some Movie"))
//        }

        val view = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_edit_quote, null, false)
        builder.setView(view)
        builder.setIcon(android.R.drawable.ic_input_add)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val quote = view.dialog_edit_text_quote.text.toString()
            val movie = view.dialog_edit_text_movie.text.toString()
            updateQuote(MovieQuote(quote, movie))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    private fun updateQuote(movieQuote: MovieQuote) {
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
            R.id.action_clear -> {
                confirmClear()
                true
            }
            R.id.action_settings -> {
                // startActivity(Intent(Settings.ACTION_SETTINGS))
                // For others, see https://developer.android.com/reference/android/provider/Settings
                // TODO: Instead of always starting the general settings, show a dialog
                // with a list of settings they can open with ACTION_SOUND_SETTINGS
                // as the first, one of your choosing for the second, and general
                // settings as the third (and as the default)
                getWhichSettings()
                true
            }
            // TODO: Create a menu item that when pressed, launches a dialog
            // to confirm the deletion. If they press OK, the quote will return to
            // the default "Movie", "Quote"
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeFontSize(delta: Int) {
        // Increase the font size by delta sp
        var currentSize = quote_text_view.textSize / resources.displayMetrics.scaledDensity
        currentSize += delta
        quote_text_view.textSize = currentSize
        movie_text_view.textSize = currentSize
    }

    private fun confirmClear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.confirm_delete_title))
        builder.setMessage(getString(R.string.confirm_delete_message))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            updateQuote(MovieQuote("Quote", "Movie"))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun getWhichSettings() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_which_settings_title))
        // For others, see https://developer.android.com/reference/android/provider/Settings
        builder.setItems(R.array.settings_types) { _, index ->
            var actionConstant = when (index) {
                0 -> Settings.ACTION_SOUND_SETTINGS
                1 -> Settings.ACTION_SEARCH_SETTINGS
                else -> Settings.ACTION_SETTINGS
            }
            startActivity(Intent(actionConstant))
        }
        builder.create().show()
    }
}
