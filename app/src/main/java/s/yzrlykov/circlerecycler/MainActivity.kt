package s.yzrlykov.circlerecycler

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import s.yzrlykov.circlerecycler.stages.Activity01CirclePoints

class MainActivity : AppCompatActivity() {

    lateinit var scrollView: NestedScrollView
    lateinit var cardsContainer: LinearLayout

    private val stages = mapOf(Activity01CirclePoints::class.java to R.string.menu_01_circle_points)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findView()
        inflateMenu(cardsContainer, stages)
    }

    private fun findView() {
        scrollView = findViewById(R.id.scroll_view)
        cardsContainer = findViewById(R.id.cards_container)
    }

    /**
     * Инфлайтим пункты меню. Каждый пункт создает отдельную активити.
     */
    private fun inflateMenu(root: LinearLayout, model: Map<out Class<*>, Int>) {

        val inflater = layoutInflater

        model.entries.forEach { entry ->
            val (clazz, stringId) = entry

            val cardView = inflater.inflate(R.layout.layout_card_main_menu, root, false)
            val titleView = cardView.findViewById<TextView>(R.id.tv_title)
            titleView.text = getString(stringId)

            cardView.setOnClickListener {
                startActivity(Intent(this, clazz))
            }

            root.addView(cardView)
        }
    }
}
