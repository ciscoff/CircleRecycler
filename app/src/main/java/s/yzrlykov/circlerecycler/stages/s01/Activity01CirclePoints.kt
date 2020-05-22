package s.yzrlykov.circlerecycler.stages.s01

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import s.yzrlykov.circlerecycler.R
import s.yzrlykov.circlerecycler.domain.Point
import s.yzrlykov.circlerecycler.domain.pointcreator.FirstQuadrantCirclePointsCreator
import s.yzrlykov.circlerecycler.logIt
import kotlin.math.min

class Activity01CirclePoints : AppCompatActivity() {

    companion object {
        val TAG = Activity01CirclePoints::class.java.simpleName
    }

    private lateinit var button: MaterialButton
    private lateinit var drawView: DrawView
    private lateinit var circlePointsCreator: FirstQuadrantCirclePointsCreator

    // Список точек
    private val points = mutableListOf<Point>()

    // Будет составлять 2/3 от наименьшей стороны экрана
    private var radius = 0

    // Центр окружности
    private val x0 = 0f
    private val y0 = 0f

    private val paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_01_circle_points)

        findViews()
        initMetrics()
        warmUp()

        initViews()
    }

    /**
     * Ищем Views
     */
    private fun findViews() {
        button = findViewById(R.id.button_draw)
        drawView = findViewById(R.id.draw_view)
    }

    /**
     * Установить обработчики на Views
     */
    private fun initViews() {

        button.setOnClickListener {
            createCirclePoints()
            drawView.invalidate()
        }
    }

    /**
     * Инициализируем математику
     */
    private fun initMetrics() {
        radius = (min(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels
        ) * 0.6f).toInt()
    }

    /**
     * Создаем нужные компоненты
     */
    private fun warmUp() {

        paint.color = Color.CYAN
        paint.strokeWidth = 1f

        circlePointsCreator = FirstQuadrantCirclePointsCreator(radius, x0.toInt(), y0.toInt())
    }

    private fun createCirclePoints() {
        circlePointsCreator.fillCirclePointsFirstQuadrant(points)
        drawView.init(points, x0 to y0)
    }
}
