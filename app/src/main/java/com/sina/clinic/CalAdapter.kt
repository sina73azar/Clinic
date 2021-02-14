package com.sina.clinic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sina.clinic.databinding.CalendarItemBinding
import net.time4j.calendar.PersianCalendar
import java.util.*

class CalAdapter(private var persianDates: List<PersianCalendar>,
                 val listener:( PersianCalendar)->Unit,
                 var selectedCal:PersianCalendar)
    : RecyclerView.Adapter<CalAdapter.CalViewHolder>() {
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
            tvMonthCalItem.text = curCal.dayOfWeek.getDisplayName(Locale("fa"))
            linearBackCalItem.setOnClickListener{
                listener(curCal)
            }
        }
        //highlight chosen one default is today
        if (position + 1 == selectedCal.dayOfMonth) {
            holder.binding.linearBackCalItem.setBackgroundResource(R.color.green)
        }else{
            holder.binding.linearBackCalItem.setBackgroundResource(R.color.green_700)
        }
    }

    override fun getItemCount(): Int {
        return persianDates.size
    }
    fun setListMonth(newList:List<PersianCalendar>){
        this.persianDates=newList
    }

}