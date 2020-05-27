package s.yzrlykov.circlerecycler.stages.s02

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.domain.PointS2
import s.yzrlykov.circlerecycler.domain.ViewData
import s.yzrlykov.circlerecycler.domain.helpers.FirstQuadrantHelper

class LayoutManagerS02(
    private val radius: Int,
    private val dimen: Int,
    private val x0: Int = 0,
    private val y0: Int = 0
) : RecyclerView.LayoutManager() {

    /**
     * Этот хелпер при инициализации самостоятельно сгенерит точки периметра окружности
     */
    private val quadrantHelper = FirstQuadrantHelper(radius, x0, y0)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        fillDown(recycler)
    }

    private fun fillDown(recycler: RecyclerView.Recycler) {
        var position = 0
        var viewTop = 0
        var fillDown = true

        // Количество элементов в адаптере
        val itemQty = itemCount

        // Стартовая точка, от которой начнет раскладывать.
        // Её центр имеет координаты (R, 0)
        val viewData = ViewData(
            0, 0, 0, 0,
            quadrantHelper.getViewCenterPoint(0)
        )

        val widthSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)

        while (fillDown && position < itemQty) {
            val view = recycler.getViewForPosition(position)

            addView(view)
            measureChildWithInsets(view, widthSpec, heightSpec)

            val viewCenter = quadrantHelper.findNextViewCenter(viewData, dimen / 2, dimen / 2)
            performLayout(view, viewCenter, dimen / 2, dimen / 2)
            viewData.updateData(view, viewCenter)

            position++
            viewTop = viewData.viewBottom
            fillDown = viewTop <= height
        }
    }

    private fun performLayout(
        view: View,
        viewCenter: PointS2,
        halfViewWidth: Int,
        halfViewHeight: Int
    ) {

        var (left, top, bottom, right) = listOf(0, 0, 0, 0)

        top = viewCenter.getY() - halfViewHeight
        bottom = viewCenter.getY() + halfViewHeight

        left = viewCenter.getX() - halfViewWidth
        right = viewCenter.getX() + halfViewWidth

        layoutDecorated(view, left, top, right, bottom)
    }

    /**
     * Вычисляем окончательный размер простанства на экране, которое займет View вместе
     * со своими маргинами и декораторами. При этом размер самой View корректируется таким
     * образом, чтобы подстроиться под размеры инсетов. То есть сама вьюха может, например,
     * уменьшиться, чтобы инсеты оставались прежними.

     */
    private fun measureChildWithInsets(child: View, widthSpec: Int, heightSpec: Int) {

        val decorRect = Rect()

        // У декоратора запрашиваем инсеты для view и получаем их в Rect
        calculateItemDecorationsForChild(child, decorRect)

        val lp = child.layoutParams as RecyclerView.LayoutParams

        // Корректируем размеры самой вьюхи, чтобы она "подвинулась" с четом размеров инсетов.
        val widthSpecUpdated = updateSpecWithExtra(
            widthSpec,
            lp.leftMargin + decorRect.left,
            lp.rightMargin + decorRect.right
        )

        val heightSpecUpdated = updateSpecWithExtra(
            heightSpec,
            lp.topMargin + decorRect.top,
            lp.bottomMargin + decorRect.bottom
        )

        child.measure(widthSpecUpdated, heightSpecUpdated)
    }

    /**
     * Корректируем отдельную размерность View (ширина/высота) view с учетом имеющихся insets.
     */
    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) {
            return spec
        }

        val mode = View.MeasureSpec.getMode(spec)

        if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            return View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset, mode
            )
        }
        return spec
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun canScrollVertically(): Boolean {
        return true
    }
}