package s.yzrlykov.circlerecycler.stages.s01

import android.graphics.Paint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import s.yzrlykov.circlerecycler.R
import s.yzrlykov.circlerecycler.domain.PointS1
import s.yzrlykov.circlerecycler.domain.Shape
import s.yzrlykov.circlerecycler.domain.pointcreator.FirstQuadrantCirclePointsCreator
import s.yzrlykov.circlerecycler.extensions.dimensionPix
import kotlin.math.abs
import kotlin.math.min

class Activity01CirclePoints : AppCompatActivity() {

    companion object {
        val TAG = Activity01CirclePoints::class.java.simpleName
    }

    private lateinit var circlePointsCreator: FirstQuadrantCirclePointsCreator
    private lateinit var button: MaterialButton
    private lateinit var drawView: DrawView
    private lateinit var tvLog: TextView

    // Список точек
    private val points = mutableListOf<PointS1>()

    // Будет составлять 2/3 от наименьшей стороны экрана
    private var radius = 0
        set(value) {
            field = abs(value.toFloat() * 0.66).toInt()
        }

    // Центр окружности
    private val x0 = 0f
    private val y0 = 0f

    private val paint = Paint()
    private val paintShape = Paint()

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
        tvLog = findViewById(R.id.tv_log)
    }

    /**
     * Установить обработчики на Views
     */
    private fun initViews() {

        button.setOnClickListener {
            it.isEnabled = false
            it.isClickable = false
            createCirclePoints()
                .subscribe {
                    drawView.invalidate()
                    it.isEnabled = true
                    it.isClickable = true
                }
        }
    }

    /**
     * Инициализируем математику
     */
    private fun initMetrics() {
        radius = min(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels
        )
    }

    /**
     * Создаем нужные компоненты
     */
    private fun warmUp() {

        paint.color = ContextCompat.getColor(this, R.color.quadrant_fill_color)
        paint.strokeWidth = 1f

        paintShape.color = ContextCompat.getColor(this, R.color.colorPrimary)

        // Закрашиваем оба сегмента одним цветом
        circlePointsCreator =
            FirstQuadrantCirclePointsCreator(radius, x0.toInt(), y0.toInt(), paint.color)
    }

    private fun createCirclePoints(): Completable {

        return Completable.create { emitter ->
            points.clear()
            circlePointsCreator.fillCirclePointsFirstQuadrant(points, paint)
            val shapes = createShapes(resources.getInteger(R.integer.shapes_qty))

            drawView
                .init(points, x0 to y0)
                .addShapes(shapes)

            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createShapes(qty: Int): List<Shape> {

        val step = points.size / (qty + 1)
        val dimen = dimensionPix(R.dimen.oval_shape_diameter)

        return arrayListOf<Shape>().apply {
            for (i in 1..qty) {
                val point = points[i * step]
                add(Shape(point.x, point.y, paintShape, dimen))
            }
        }
    }
}
