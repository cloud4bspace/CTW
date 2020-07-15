package space.cloud4b.ctw.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.entry_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R
import space.cloud4b.ctw.model.CakeboardEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

class CakeboardAdapter(var entries: MutableList<CakeboardEntry>, var context: Context) : BaseAdapter() {

    var layoutInflater : LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(index: Int, oldView: View?, viewGroup: ViewGroup?): View {
        var view : View
        if(oldView == null) {
            view = layoutInflater.inflate(R.layout.entry_cell, null)
        } else {
            view = oldView
        }
        val entry:CakeboardEntry = getItem(index)
        view.tvMemberName.text = entry.MemberName
        view.tvEntryId.text = "#${entry.entryId.toString()}"
        view.tvEntryDate.text = entry.entryDate.toString()
        var date = LocalDate.parse(entry.entryDate.toString())
        view.tvEntryDate.text = date.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG))
        view.tvReason.text = entry.ListReason

        view.ivReason.setImageResource(R.drawable.ic_baseline_cake_24)

        return view
    }

    override fun getItem(index: Int): CakeboardEntry {
        return entries.get(index)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return entries.size
    }

    fun getUserName() : MutableList<CakeboardEntry> {
        return entries
    }
}