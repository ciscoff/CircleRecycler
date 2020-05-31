package s.yzrlykov.circlerecycler.stages.s03_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CustomView03 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), EventObservable<String> {

    private var countMeasure = 1
    private var countLayout = 1
    private var countDraw = 1

    private val measureObs = BehaviorSubject.create<String>()
    private val layoutObs = BehaviorSubject.create<String>()
    private val drawObs = BehaviorSubject.create<String>()

    private val combinedObs = Observable.combineLatest(
        measureObs,
        layoutObs,
        drawObs,
        Function3 { measure: String, layout: String, position: String ->
            "${measure}\n${layout}\n${position}"
        }
    )

    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 30f
        textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureObs.onNext("measure: ${countMeasure++}")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val params = layoutParams as FrameLayout.LayoutParams
        val message =
            "layout: ${countLayout++}\n  topMargin: ${params.topMargin}\n  leftMargin: ${params.leftMargin}\n  x = $left, y = $top"

        layoutObs.onNext(message)
    }

    override fun connectTo(): Observable<String> {
        return combinedObs
    }

    override fun onDraw(canvas: Canvas?) {
        drawObs.onNext("draw: ${countDraw++}")

        // Вот так не надо !
//        text = "x = $x\ny = $y"

        // Вот так надо:
        // Выравниваем текст горизонтально по центру благодаря Paint.Align.CENTER
        canvas?.let {
            it.drawText("x = $x", (width / 2).toFloat(), (height / 3).toFloat(), paint)
            it.drawText("y = $y", (width / 2).toFloat(), (height / 3) * 2f, paint)
        }
    }
}