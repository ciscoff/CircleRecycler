package s.yzrlykov.circlerecycler.stages.s01

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import s.yzrlykov.circlerecycler.domain.PointS1
import s.yzrlykov.circlerecycler.domain.Shape

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var pointS1s: List<PointS1>
    private val shapes = mutableListOf<Shape>()

    private var x0 = 0f
    private var y0 = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (::pointS1s.isInitialized) {

            for (point in pointS1s) {
                canvas.drawLine(x0, y0, point.x.toFloat(), point.y.toFloat(), point.paint)
            }

            shapes.forEach { shape ->
                canvas.drawOval(shape, shape.paint)
            }
        }
    }

    fun init(pointS1s: List<PointS1>, origin: Pair<Float, Float>): DrawView {
        this.pointS1s = pointS1s
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