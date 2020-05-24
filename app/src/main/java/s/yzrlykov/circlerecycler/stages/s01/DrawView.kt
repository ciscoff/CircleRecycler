package s.yzrlykov.circlerecycler.stages.s01

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import s.yzrlykov.circlerecycler.domain.Point
import s.yzrlykov.circlerecycler.domain.Shape

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var points: List<Point>
    private val shapes = mutableListOf<Shape>()

    private var x0 = 0f
    private var y0 = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (::points.isInitialized) {

            for (point in points) {
                canvas.drawLine(x0, y0, point.x.toFloat(), point.y.toFloat(), point.paint)
            }

            shapes.forEach { shape ->
                canvas.drawOval(shape, shape.paint)
            }
        }
    }

    fun init(points: List<Point>, origin: Pair<Float, Float>): DrawView {
        this.points = points
        x0 = origin.first
        y0 = origin.second
        return this
    }

    fun addShapes(_shapes: List<Shape>): DrawView {
        shapes.apply {
            clear()
            addAll(_shapes)
        }
        return this
    }
}