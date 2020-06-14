package s.yzrlykov.circlerecycler.stages.s03

import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.domain.PointS2
import s.yzrlykov.circlerecycler.domain.UpdatablePoint
import s.yzrlykov.circlerecycler.domain.ViewData
import s.yzrlykov.circlerecycler.domain.helpers.FirstQuadrantHelper
import s.yzrlykov.circlerecycler.logIt
import kotlin.math.min

class LayoutManagerS03(
    private val radius: Int,
    private val dimen: Int,
    private val x0: Int = 0,
    private val y0: Int = 0
) : RecyclerView.LayoutManager() {

    private val helperPoint =
        UpdatablePoint(0, 0)

    /**
     * Кэш View для работы со скролом
     */
    private val viewCache = SparseArray<View>()

    /**
     * Этот хелпер при инициализации самостоятельно сгенерит точки периметра окружности
     */
    private val quadrantHelper = FirstQuadrantHelper(radius, x0, y0)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {

        // Метод ОБЯЗАТЕЛЕН !!!
        detachAndScrapAttachedViews(recycler)
        fillDown(recycler)
    }

    private fun fillDown(recycler: RecyclerView.Recycler) {
        var position = 0
        var viewTop = 0
        var fillDown = true


        // Количество элементов в адаптере
        val itemQty = itemCount

        // Стартовая точка, от которой начнем раскладывать.
        // Её центр имеет координаты (R, 0) и размеры 0х0
        val viewData = ViewData(
            0, 0, 0, 0,
            quadrantHelper.getViewCenterPoint(0)
        )

        val widthSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(dimen, View.MeasureSpec.EXACTLY)

        val view = recycler.getViewForPosition(position)
        view.tag = System.currentTimeMillis()
        addView(view)
        measureChildWithInsets(view, widthSpec, heightSpec)

        val viewCenter = quadrantHelper.findNextViewCenter(viewData, dimen / 2, dimen / 2)
        performLayout(view, viewCenter, dimen / 2, dimen / 2)
        viewData.updateData(view, viewCenter)

//        position++
//        viewTop = viewData.viewBottom
//        fillDown = isLastLaidOutView(viewTop)


//        while (fillDown && position < itemQty) {
//            val view = recycler.getViewForPosition(position)
//
//            addView(view)
//            measureChildWithInsets(view, widthSpec, heightSpec)
//
//            val viewCenter = quadrantHelper.findNextViewCenter(viewData, dimen / 2, dimen / 2)
//            performLayout(view, viewCenter, dimen / 2, dimen / 2)
//            viewData.updateData(view, viewCenter)
//
//            position++
//            viewTop = viewData.viewBottom
//            fillDown = isLastLaidOutView(viewTop)
//        }
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {

        if (childCount == 0) return 0

//        val delta = calculateVerticalScrollOffset(dy)
//        offsetChildrenVertical(-delta)
//        fill(recycler)

        val delta = calculateVerticalDelta(dy)
        getChildAt(0)?.let {
            scrollSingleView(it, -delta)
        }

        return delta
    }

    private fun calculateVerticalDelta(dy: Int): Int {

        // Количество приаттаченных элементов к RecyclerView (Наверное количество видимых элементов)
        val childCount = childCount

        if (childCount == 0) {
            return 0
        }

        // Случай, когда View достигла края верхнего или нижнего
        getChildAt(0)?.let { view ->

            val viewTop = view.top

            return if((inTopPosition(view) && dy > 0) || (inBottomPosition(view) && dy < 0)) {
                0
            } else if (viewTop > 0 && dy > 0) {
                min(dy, viewTop)
            } else {
                dy
            }
        }

        return dy
    }

    private fun inTopPosition(view : View) : Boolean {
        return view.top <= 0
    }

    private fun inBottomPosition(view : View) : Boolean {
        return view.top == (radius - dimen / 2) || view.left <= 0
    }

    /**
     * View может максимально опуститься на (radius - dimen / 2).
     * Потом начнет подниматься во втором квадранте.
     */
    private fun isLastLaidOutView(viewTop: Int): Boolean {
        return viewTop == (radius - dimen / 2)
    }

    private fun scrollSingleView(view: View, _dy: Int) {
        logIt("Scrolling view with tag ${view.tag as Long}")

        val centerX = view.right - dimen / 2
        val centerY = view.bottom - dimen / 2

        helperPoint.update(centerX, centerY)

        val centerPointIndex = quadrantHelper.getViewCenterPointIndex(helperPoint)
        val newCenterPointIndex = quadrantHelper.getNewCenterPointIndex(centerPointIndex + _dy)

        val newCenterPoint = quadrantHelper.getViewCenterPoint(newCenterPointIndex)

        val dx = newCenterPoint.x - centerX
        val dy = newCenterPoint.y - centerY

        view.offsetTopAndBottom(dy)
        view.offsetLeftAndRight(dx)
    }

    /**
     * Вычислить реальный офсет прокрутки
     *
     * Вот важно по поводу layout
     * https://stackoverflow.com/questions/33196553/android-difference-between-offsettopandbottom-settranslatey
     *
     *
     */
    private fun calculateVerticalScrollOffset(dy: Int): Int {

        // Количество приаттаченных элементов к RecyclerView (Наверное количество видимых элементов)
        val childCount = childCount
        // Количество элементов в адаптере
        val itemCount = itemCount

        if (childCount == 0) {
            return 0
        }

        val topView = getChildAt(0)
        val bottomView = getChildAt(childCount - 1)

        // Случай, когда все вьюшки адаптера поместились на экране
        topView?.let { tv ->
            bottomView?.let { bv ->

                if (getDecoratedTop(tv) >= 0 &&
                    getDecoratedBottom(bv) <= height &&
                    getDecoratedLeft(bv) >= 0
                ) {
                    return 0
                }
            }
        }

        var delta = 0

        // Если палец идет вниз, то есть прокручиваем к верхней части контента
        if (dy < 0) {

            val firstVisibleView = getChildAt(0)!!
            val firstVisibleViewAdapterPos = getPosition(firstVisibleView)

        } else {

        }
        return 0
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

    /**
     * Первая сверху вьюха, которая полностью видна
     */
    private fun getAnchorView(): View? {

        (0 until childCount).forEach { i ->
            getChildAt(i)?.let { view ->
                if (getDecoratedTop(view) >= 0 && getDecoratedBottom(view) <= height) {
                    return view
                }
            }
        }

        return null
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

    /**
     * Набор методов, которыми мы располагаем
     */
    private fun arsenal(recycler: RecyclerView.Recycler) {

        // Это индекс видимого элемента внутри RecyclerView
        val i = 0
        // Это индекс в адаптере
        var position = 0

        /**
         * Данные из RecyclerView
         */
        // Количество видимых сейчас элементов
        val param1 = getChildCount()

        // View видимого элемента с индексом i
        val viewItem = getChildAt(i)

        /**
         * Данные из адаптера (recycler'a)
         */

        // View для элемента адаптера с индексом position
        val view = recycler.getViewForPosition(position)

        // Количество элементов в адаптере
        val param2 = getItemCount()

        // Позиция View в адаптере
        position = getPosition(view)

    }

}