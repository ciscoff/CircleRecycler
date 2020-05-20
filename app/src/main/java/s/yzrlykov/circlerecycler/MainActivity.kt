package s.yzrlykov.circlerecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private val menus = listOf("First", "Second", "Third", "First", "Second", "Third", "First", "Second", "Third", "First", "Second", "Third", "First", "Second", "Third", "First", "Second", "Third")

    lateinit var scrollView : NestedScrollView
    lateinit var cardsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findView()
        inflateMenu(cardsContainer, menus)
    }

    private fun findView() {
        scrollView = findViewById(R.id.scroll_view)
        cardsContainer = findViewById(R.id.cards_container)
    }

    private fun inflateMenu(root: LinearLayout, titles : List<String>) {

        val inflater = layoutInflater

        titles.forEach {
            val cardView = inflater.inflate(R.layout.layout_card_main_menu, root, false)
            val titleView = cardView.findViewById<TextView>(R.id.tv_title)
            titleView.text = it
            root.addView(cardView)
        }
    }
}
