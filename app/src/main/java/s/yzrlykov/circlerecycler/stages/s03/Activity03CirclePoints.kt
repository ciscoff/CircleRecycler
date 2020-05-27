package s.yzrlykov.circlerecycler.stages.s03

import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.R
import s.yzrlykov.circlerecycler.domain.PointS2
import s.yzrlykov.circlerecycler.domain.pointcreator.FirstQuadrantCirclePointsCreator
import s.yzrlykov.circlerecycler.extensions.dimensionPix
import kotlin.math.abs
import kotlin.math.min

class Activity02CirclePoints : AppCompatActivity() {
    private lateinit var circlePointsCreator: FirstQuadrantCirclePointsCreator
    private lateinit var recyclerView: RecyclerView

    // Список точек
    private val mapIndexPoint = mutableMapOf<Int, PointS2>()
    private val mapPointIndex = mutableMapOf<PointS2, Int>()

    // Центр окружности
    private val x0 = 0f
    private val y0 = 0f

    private val paint = Paint()

    private var radius = 0
        set(value) {
            field = abs(value.toFloat() * 0.66).toInt()
        }

    private var dimenListItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_03_vertical_scroll)

        initMetrics()
        findViews()
        warmUp()
        initRecyclerView(AdapterS03(resources.getStringArray(R.array.list_item_titles).toList()))
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.recycler_view)
    }

    /**
     * Инициализируем математику
     */
    private fun initMetrics() {
        radius = min(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels
        )

        dimenListItem = dimensionPix(R.dimen.list_item_dimen)
    }

    private fun warmUp() {
        paint.color = ContextCompat.getColor(this, R.color.quadrant_fill_color)
        paint.strokeWidth = 1f

        // Закрашиваем оба сегмента одним цветом
        circlePointsCreator =
            FirstQuadrantCirclePointsCreator(radius, x0.toInt(), y0.toInt(), paint.color)

        createCirclePoints()
    }

    /**
     * Создать "массивы" точек периметра окружности
     */
    private fun createCirclePoints() {
        mapIndexPoint.clear()
        mapPointIndex.clear()
        circlePointsCreator.fillCirclePoints(mapIndexPoint, mapPointIndex)
    }

    private fun initRecyclerView(adapterS03: AdapterS03) {

        recyclerView.apply {
            //            setHasFixedSize(true)
//            itemAnimator = DefaultItemAnimator()
            adapter = adapterS03
            layoutManager = LayoutManagerS03(radius, dimenListItem, 0, 0)
        }
    }

}