package s.yzrlykov.circlerecycler.stages.s01

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import s.yzrlykov.circlerecycler.domain.Point

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var points : List<Point>
    private var x0 = 0f
    private var y0 = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(::points.isInitialized) {

            for(point in points) {
                canvas.drawLine(x0, y0, point.x.toFloat(), point.y.toFloat(), point.getPaint())
            }
        }
    }

    fun init(points : List<Point>, origin : Pair<Float, Float>) {
        this.points = points
        x0 = origin.first
        y0 = origin.second
    }
}