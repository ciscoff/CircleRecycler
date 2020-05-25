package s.yzrlykov.circlerecycler.stages.s02

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.domain.PointS1

class LayoutManagerS02(
    private val radius: Int,
    private val dimen: Int,
    private val pointS1s: List<PointS1>
) : RecyclerView.LayoutManager() {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        fillDown(recycler)
    }

    private fun fillDown(recycler : RecyclerView.Recycler) {
        var position = 0
        var fillDown = true

        var viewTop : Int
        var viewLeft : Int
        val height = pointS1s.last().y + dimen/2

        // Количество элементов в адаптере
        val itemCount = getItemCount()

        val widthSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)

        while (fillDown && position < itemCount) {

            // Координата Y (верхней границы View) вычисляется просто
            viewTop = position * dimen
            // Координата X корректируется "по окружности". Сначала находим точку
            // центра View, а затем смещаем левее на пол ширины. Получаем координату
            // левой границы View.
            viewLeft = pointS1s[viewTop + dimen/2].x - dimen/2

            val view = recycler.getViewForPosition(position)

            addView(view)
            measureChildWithInsets(view, widthSpec, heightSpec)

            layoutDecorated(view,
                viewLeft,
                viewTop,
                viewLeft + dimen,
                viewTop+ dimen)

            position++
            viewTop = position * dimen
            fillDown = viewTop <= height && position * dimen < pointS1s.lastIndex
        }
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