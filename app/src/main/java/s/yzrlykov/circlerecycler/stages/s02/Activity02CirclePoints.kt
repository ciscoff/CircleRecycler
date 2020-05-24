package s.yzrlykov.circlerecycler.stages.s02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import s.yzrlykov.circlerecycler.R

class Activity02CirclePoints : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_02_vertical_scroll)

        findViews()
        initRecyclerView(AdapterS02(resources.getStringArray(R.array.list_item_titles).toList()))
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun initRecyclerView(adapterS02: AdapterS02) {

        recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = adapterS02
            layoutManager = LinearLayoutManager(this@Activity02CirclePoints, RecyclerView.VERTICAL, false)
        }
    }

}