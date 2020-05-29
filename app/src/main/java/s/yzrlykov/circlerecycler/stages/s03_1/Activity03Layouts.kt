package s.yzrlykov.circlerecycler.stages.s03_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import s.yzrlykov.circlerecycler.R
import s.yzrlykov.circlerecycler.extensions.px

class Activity03Layouts : AppCompatActivity() {

    private lateinit var seekBar1: SeekBar
    private lateinit var seekBar2: SeekBar
    private lateinit var seekBar3: SeekBar
    private lateinit var seekBar4: SeekBar

    private lateinit var textView: TextView

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
    }

    private fun initViews() {
        seekBar1.setOnSeekBarChangeListener(seekChangeListener)
        seekBar2.setOnSeekBarChangeListener(seekChangeListener)
        seekBar3.setOnSeekBarChangeListener(seekChangeListener)
        seekBar4.setOnSeekBarChangeListener(seekChangeListener)
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
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
}
