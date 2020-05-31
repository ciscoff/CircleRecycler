package s.yzrlykov.circlerecycler.stages.s03_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import s.yzrlykov.circlerecycler.R
import s.yzrlykov.circlerecycler.extensions.px

class Activity03Layouts : AppCompatActivity() {

    private lateinit var seekBar1: SeekBar
    private lateinit var seekBar2: SeekBar
    private lateinit var seekBar3: SeekBar
    private lateinit var seekBar4: SeekBar

    private lateinit var textView: TextView
    private lateinit var textConsole1 : TextView
    private lateinit var textConsole2 : TextView
    private lateinit var frameLayout : FrameLayout

    private var seekMax = 0
    private var seekInit = 0
    private var lastProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity03_layouts)

        seekMax = resources.getInteger(R.integer.seek_max)
        seekInit = resources.getInteger(R.integer.seek_init)
        lastProgress = seekInit

        findViews()
        initViews()
    }

    private fun findViews() {
        seekBar1 = findViewById(R.id.seek_bar_1)
        seekBar2 = findViewById(R.id.seek_bar_2)
        seekBar3 = findViewById(R.id.seek_bar_3)
        seekBar4 = findViewById(R.id.seek_bar_4)
        textView = findViewById(R.id.tv_tv)
        frameLayout = findViewById(R.id.frame_layout)
        textConsole1 = findViewById(R.id.tv_console_1)
        textConsole2 = findViewById(R.id.tv_console_2)
    }

    private fun initViews() {
        seekBar1.setOnSeekBarChangeListener(seekChangeListener)
        seekBar2.setOnSeekBarChangeListener(seekChangeListener)
        seekBar3.setOnSeekBarChangeListener(seekChangeListener)
        seekBar4.setOnSeekBarChangeListener(seekChangeListener)

        (textView as EventObservable<String>)
            .connectTo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::childEventObserver)

        (frameLayout as EventObservable<String>)
            .connectTo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::parentEventObserver)
    }

    private fun childEventObserver(event : String) {
        textConsole1.text = event
    }

    private fun parentEventObserver(event : String) {
        textConsole2.text = event
    }

    /**
     * offsetLeftAndRight/offsetTopAndBottom задают смещение от текущей позиции View
     * Поэтому в коде ниже для каждого очередного значения прогресса высчитывается разница
     * между его новым значением и предыдущим и на эту разницу двигаем View. Если просто
     * двигать на значение прогресса, то View быстро уедет за экран.
     *
     * translationX/translationY задают смещение от "опорного" начального положения top/left
     * элемента в layout'е
     *
     * Все рассмотренные смещения - знаковые.
     *
     * Как выяснилось - при перемещении View таким образом, у него не вызывается ни onMeasure ни
     * onLayout. Странно. Вроде положение меняется, но ничего не происходит.
     * И маргины тоже не меняются при перемещении.
     *
     * В документации сказано, что onLayout должно инициироваться из родителя, если с ним что-то
     * происходит и ЕГО СОБСТВЕННЫЕ размеры и положение зависят от дочерних элементов (например
     * у родителя стоит wrap_content). А если родителю пох перемещения его детей, то он у них
     * ничего такого не вызывает.
     */
    private val seekChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

            when (seekBar.id) {
                R.id.seek_bar_1 -> {
                    textView.offsetLeftAndRight((progress - lastProgress).px)
                    lastProgress = progress
                }
                R.id.seek_bar_2 -> {
                    textView.offsetTopAndBottom((progress - lastProgress).px)
                    lastProgress = progress
                }

                R.id.seek_bar_3 -> {
                    textView.translationX = (progress - seekInit).px.toFloat()
                }

                R.id.seek_bar_4 -> {
                    textView.translationY = (progress - seekInit).px.toFloat()
                }
            }

            /**
             * Почитать:
             * https://bit.ly/2XKlW69
             * Посмотреть:
             * https://www.youtube.com/watch?v=86p1GPEv_fY&t=5m42s
             *
             * При движении seekBar'a мы меняем различные смещения у textView и элемент
             * передвигается (изменяются его X/Y), НО при этом не вызывается ни один из,
             * методов onMeasure onLayout, onDraw.
             *
             * - onMeasure не вызывается, потому что родительский компонент не меняется и ему
             *   не нужно переизмерять вложенные элементы.
             * - onLayout не вызывается по той же причине: родительский элемент на том же месте
             * - onDraw потому что ВНУТРИ textView ничего не надо перерисовывать.
             *
             * Поэтому нужно принудительно перерисовать textView через invalidate. В этот
             * момент он прочитает новые значения X/Y и выведет на экран.
             *
             *
             * view.requestLayout() - инициирует запрос ВВЕРХ к родителю о необходимости
             *   выполнить layout. Родитель ставит в очередь UI-потока задание на layout
             *   (это будет measure + layout).
             */
//            textView.requestLayout()
            textView.invalidate()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
}
