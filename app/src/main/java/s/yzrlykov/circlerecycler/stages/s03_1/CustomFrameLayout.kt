package s.yzrlykov.circlerecycler.stages.s03_1

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CustomFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), EventObservable<String> {

    private var countMeasure = 1
    private var countLayout = 1
    private var countDraw = 1

    private val measureObs = BehaviorSubject.createDefault<String>("$countMeasure")
    private val layoutObs = BehaviorSubject.createDefault<String>("$countLayout")
    private val drawObs = BehaviorSubject.createDefault<String>("draw: $countDraw")

    private val combinedObs = Observable.combineLatest(
        measureObs,
        layoutObs,
        drawObs,
        Function3 { measure: String, layout: String, position: String ->
            "${measure}\n${layout}\n${position}"
        }
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (countMeasure % 10 == 0) {
            measureObs.onNext("measure: ${countMeasure++}")
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (countLayout % 10 == 0) {
            layoutObs.onNext("layout: ${countLayout++}")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawObs.onNext("draw: ${countDraw++}")
    }

    override fun connectTo(): Observable<String> {
        return combinedObs
    }
}