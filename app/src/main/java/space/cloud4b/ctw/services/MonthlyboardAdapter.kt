package space.cloud4b.ctw.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import kotlinx.android.synthetic.main.monthlyentry_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R
import space.cloud4b.ctw.model.MonthlyReason
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

class MonthlyboardAdapter(var entries: MutableList<MonthlyReason>, var context: Context) : BaseAdapter() {

    var layoutInflater : LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(index: Int, oldView: View?, viewGroup: ViewGroup?): View {
        var view : View
        if(oldView == null) {
            view = layoutInflater.inflate(R.layout.monthlyentry_cell, null)
        } else {
            view = oldView
        }
        val entry:MonthlyReason = getItem(index)
        view.tvMonthlyReasonText.text = entry.Anzahl + " x " + entry.ListReason
        view.ivMonthlyReasonIcn.setImageResource(context.getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(entry.ListReason)}",null,null))
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(5, 5, 5, 5)
        view.ivMonthlyReasonIcn.setLayoutParams(lp)

        return view
    }

    override fun getItem(index: Int): MonthlyReason {
        return entries.get(index)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return entries.size
    }

    fun getUserName() : MutableList<MonthlyReason> {
        return entries
    }
}