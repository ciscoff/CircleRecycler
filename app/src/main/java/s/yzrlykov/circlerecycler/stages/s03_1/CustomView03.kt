package s.yzrlykov.circlerecycler.stages.s03_1

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class CustomView03 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) , EventObservable<String>{

    private var countMeasure = 1
    private var countLayout = 1

    private val measureObs = PublishSubject.create<String>()
    private val layoutObs = PublishSubject.create<String>()

    private val eventsObs = Observable.combineLatest(
        measureObs,
        layoutObs,
        BiFunction { measure: String, layout: String ->
            "${measure}, ${layout}"
        }
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureObs.onNext("measure: ${countMeasure++}")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val params = layoutParams as FrameLayout.LayoutParams

        val message = "layout: ${countLayout++}, topMargin: ${params.topMargin}, leftMargin: ${params.leftMargin}"
        layoutObs.onNext(message)
    }

    override fun connectTo(): Observable<String> {
        return eventsObs
    }
}