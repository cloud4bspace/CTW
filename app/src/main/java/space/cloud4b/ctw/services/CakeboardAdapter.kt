package space.cloud4b.ctw.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R
import space.cloud4b.ctw.model.CakeboardEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

/**
 * Die Klasse stellt einen Adapter zur Verfügung, um die ListView (entrylist_fragment.xml)
 * mit Daten zu befüllen.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
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

        // Infotext zusammenstellen aus der Liste an Food&Beverages und den Bemerkungen
        var infoText = entry.ListFoodAndBev.replace("|", ", ")
        if(!entry.ListDescription.isEmpty()){
            infoText += "\n" + entry.ListDescription
        }

        view.tvInfos.text = infoText.trim()
        view.tvEntryId.text = "#${entry.entryId.toString()}"
        var date = LocalDate.parse(entry.entryDate.toString())
        view.tvEntryDate.text = date.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG))
        view.tvName.text = entry.MemberName

        // Icon für Grund ermitteln
        var icnName = IconMapper().getIcnName(entry.ListReason)
        view.ivReason.setImageResource(context.getResources().getIdentifier(
            "space.cloud4b.ctw:drawable/$icnName",null,null))
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(10, 10, 10, 10)
        view.ivReason.setLayoutParams(lp)

        // Icon für Tageszeit
        icnName = IconMapper().getIcnName(entry.ListDaytime)
        view.ivDayTime.setImageResource(context.getResources().getIdentifier(
            "space.cloud4b.ctw:drawable/$icnName",null,null))
        view.ivDayTime.setLayoutParams(lp)

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