package com.sina.clinic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sina.clinic.databinding.ActivityMainBinding
import net.time4j.PlainDate
import net.time4j.SystemClock
import net.time4j.calendar.PersianCalendar
import net.time4j.calendar.PersianMonth
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CalAdapter
    private lateinit var selectedCalendar: PersianCalendar
    private lateinit var listCurrentPersianCalendar:List<PersianCalendar>
    private var todayPersianCalendar:PersianCalendar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //first get right now
        getRightNow()

        todayPersianCalendar?.apply {
            selectedCalendar=this
            binding.tvMonth.text= this.month.getDisplayName(Locale("fa")) + this.year
            monthBuilder(this.year, this.month)
        }
        adapter=CalAdapter(listCurrentPersianCalendar)
        binding.rvPersianCal.adapter = adapter

        binding.viewNextMonth.setOnClickListener {
            val newYear=computeNextYear(selectedCalendar)
            val newMonth=computeNextMonth(newYear)
            selectedCalendar = PersianCalendar.of(
                newYear,
                newMonth,
                selectedCalendar.dayOfMonth
            )
            changeMonth()
            monthBuilder(selectedCalendar.year,selectedCalendar.month)
            adapter.setListMonth(listCurrentPersianCalendar)
            adapter.notifyDataSetChanged()
        }
        binding.viewLastMonth.setOnClickListener {
            val newYear=computeLastYear(selectedCalendar)
            val newMonth=computeLastMonth(newYear)
            selectedCalendar = PersianCalendar.of(
                newYear,
                newMonth,
                selectedCalendar.dayOfMonth
            )
            changeMonth()
            monthBuilder(selectedCalendar.year,selectedCalendar.month)
            adapter.setListMonth(listCurrentPersianCalendar)
            adapter.notifyDataSetChanged()
        }
    }
    private fun computeLastMonth(newYear: Int): Int {
        return if (selectedCalendar.year == newYear)
            selectedCalendar.month.value.minus(1)
        else 12
    }
    private fun computeLastYear(selectedCalendar: PersianCalendar): Int {
        selectedCalendar.let {
            return if(it.month.value==1){
                it.year-1
            }else{
                it.year
            }
        }
    }
    private fun computeNextMonth(newYear: Int):Int {
        return if (selectedCalendar.year == newYear)
            selectedCalendar.month.value.plus(1)
        else 1
    }
    private fun computeNextYear(selectedCalendar: PersianCalendar):Int {
        selectedCalendar.let {
            return if(it.month.value==12){
                it.year+1
            }else{
                it.year
            }
        }
    }
    private fun changeMonth() {
        selectedCalendar?.apply {
            binding.tvMonth.text= this.month.getDisplayName(Locale("fa")) + this.year
        }
    }
    private fun getRightNow() {
//        val formatter = ChronoFormatter.setUp(PersianCalendar.axis(), Locale("fa"))
//            .addPattern("EEE, d. MMMM yy", PatternType.CLDR).build()
        val today: PlainDate = SystemClock.inLocalView().today()
        todayPersianCalendar=today.transform(PersianCalendar::class.java)
    }
    private fun monthBuilder(year: Int, month: PersianMonth?) {
        month?.value?.let {
            val tempList= mutableListOf<PersianCalendar>()
            for (i in 1..getLengthPersianMonth(it)) {
                tempList.add(PersianCalendar.of(year, month, i))
            }
            listCurrentPersianCalendar=tempList
        }
    }
    private fun getLengthPersianMonth(monthNumber:Int):Int{
        return when (monthNumber) {
            in 1..6 -> 31
            12 -> 29
            else -> 30
        }
    }
}