package com.sina.clinic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sina.clinic.databinding.CalendarItemBinding
import net.time4j.calendar.PersianCalendar
import java.util.*

class CalAdapter(private var persianDates: List<PersianCalendar>) : RecyclerView.Adapter<CalAdapter.CalViewHolder>() {
    lateinit var binding: CalendarItemBinding
    class CalViewHolder(val binding: CalendarItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalViewHolder {
        binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalViewHolder, position: Int) {
        val curCal=persianDates[position]
        holder.binding.apply {
            tvDayCalItem.text=curCal.dayOfMonth.toString()
            tvMonthCalItem.text=curCal.dayOfWeek.getDisplayName(Locale("fa"))
        }
    }

    override fun getItemCount(): Int {
        return persianDates.size
    }
    fun setListMonth(newList:List<PersianCalendar>){
        this.persianDates=newList
    }
}